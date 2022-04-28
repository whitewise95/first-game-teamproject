package com.study.spring.controller;

import com.study.spring.config.auth.dto.OAuth;
import com.study.spring.domain.User;
import com.study.spring.service.MemberService;
import org.slf4j.*;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.Optional;

@RestController
@RequestMapping("/member")
public class MemberController {

    private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

    private MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    /*
     * Oauth2 유저 값 세션 저장 후 창 띄어주기
     *
     * */
    @PostMapping("/login")
    public OAuth login(OAuth oAuth) throws Exception {
        Integer cnt = 0;
        String os = System.getProperty("os.name");
        Runtime runtime = Runtime.getRuntime();
        String url = "http://localhost:8091/oauth/" + oAuth.getPlatform() + "/" + oAuth.getUniqueNumber();
        if (os.startsWith("Windows")) {
            String cmd = "rundll32 url.dll,FileProtocolHandler " + url;
            Process p = runtime.exec(cmd);
        } else if (os.startsWith("Mac OS")) {
            Class fileMgr = Class.forName("com.apple.eio.FileManager");
            Method openURL = fileMgr.getDeclaredMethod("openURL", new Class[] { String.class });
            openURL.invoke(null, new Object[] { url });
        }
        OAuth responseOAuth = null;
        while (true) {
            responseOAuth = memberService.socialSelect(oAuth);
            if (Optional.ofNullable(oAuth).isPresent()) {
                break;
            }
            if(cnt > 100000) {
                break;
            }
            cnt++;
        }
        return responseOAuth;
    }

}

