package com.study.spring.controller;

import com.study.spring.domain.Login;
import com.study.spring.dto.OAuth;
import com.study.spring.service.MemberService;
import com.study.spring.service.common.CommonService;
import org.apache.commons.codec.binary.StringUtils;
import org.slf4j.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.Optional;

@RestController
@RequestMapping("/member")
public class MemberController {

    private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

    private final MemberService memberService;
    private final CommonService commonService;

    public MemberController(MemberService memberService, CommonService commonService) {
        this.memberService = memberService;
        this.commonService = commonService;
    }

    /*
     * Oauth2 유저 값 세션 저장 후 창 띄어주기
     *
     * */
    @PostMapping("/login")
    public String login(@RequestBody @Validated(Login.class) OAuth oAuth) throws Exception {
        if(!platformCheck(oAuth.getPlatform())){
            return "not find platform :"+ oAuth.getPlatform();
        }

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
        String token = null;
        while (true) {
            responseOAuth = memberService.socialSelect(oAuth);
            if (Optional.ofNullable(responseOAuth).isPresent()) {
                token = commonService.createJwt(responseOAuth.getUid());
                break;
            }
            if (cnt > 100000) {
                break;
            }
            cnt++;
        }
        return token;
    }

    private boolean platformCheck(String platform) {
        if (!StringUtils.equals(platform, "google") && !StringUtils.equals(platform, "facebook")) {
            return false;
        }
        return true;
    }

}

