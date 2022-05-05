package com.study.spring.controller;

import com.study.spring.domain.Login;
import com.study.spring.dto.OAuth;
import com.study.spring.dto.common.resultType.Platform;
import com.study.spring.service.MemberService;
import com.study.spring.service.common.CommonService;
import org.apache.commons.codec.binary.StringUtils;
import org.slf4j.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;

import java.awt.*;
import java.io.*;
import java.lang.reflect.Method;

@RestController
@RequestMapping("/member")
public class MemberController {

    private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

    private final MemberService memberService;
    private final CommonService commonService;
    private final ThreadService threadService;

    public MemberController(MemberService memberService, CommonService commonService, ThreadService threadService) {
        this.memberService = memberService;
        this.commonService = commonService;
        this.threadService = threadService;
    }

    /*
     * Oauth2 유저 값 세션 저장 후 창 띄어주기
     *
     * */
    //    @PostMapping("/login")
    //    public String login(@RequestBody @Validated(Login.class) OAuth oAuth) throws Exception {
    //        if(!platformCheck(oAuth.getPlatform())){
    //            return "not find platform :"+ oAuth.getPlatform();
    //        }
    //
    //        Integer cnt = 0;
    //        String os = System.getProperty("os.name");
    //        Runtime runtime = Runtime.getRuntime();
    //        String url = "http://localhost:8091/oauth/" + oAuth.getPlatform() + "/" + oAuth.getUniqueNumber();
    //        if (os.startsWith("Windows")) {
    //            String cmd = "rundll32 url.dll,FileProtocolHandler " + url;
    //            Process p = runtime.exec(cmd);
    //        } else if (os.startsWith("Mac OS")) {
    //            Class fileMgr = Class.forName("com.apple.eio.FileManager");
    //            Method openURL = fileMgr.getDeclaredMethod("openURL", new Class[] { String.class });
    //            openURL.invoke(null, new Object[] { url });
    //        }
    //        OAuth responseOAuth = null;
    //        String token = null;
    //        while (true) {
    //            responseOAuth = memberService.socialSelect(oAuth);
    //            if (Optional.ofNullable(responseOAuth).isPresent()) {
    //                token = commonService.createJwt(responseOAuth.getUid());
    //                break;
    //            }
    //            if (cnt > 100000) {
    //                break;
    //            }
    //            cnt++;
    //        }
    //        return token;
    //    }

    @PostMapping("/login")
    public OAuth login(@Validated(Login.class) OAuth oAuth) throws Exception {
        if (!StringUtils.equals(oAuth.getPlatform(), Platform.GOOGLE.getPlatform()) && !StringUtils.equals(oAuth.getPlatform(), Platform.FACEBOOK.getPlatform())) {
            throw new RuntimeException("플랫폼이 잘못되었습니니다. platform : " + oAuth.getPlatform());
        }

        String url = "http://wisebookshop.com/oauth/" + oAuth.getPlatform() + "/" + oAuth.getUniqueNumber();
//        try {
//            Desktop desktop = Desktop.getDesktop();
//            if (desktop.isSupported(Desktop.Action.OPEN)) {
//                desktop.open(new File(url));
//            } else {
//                System.out.println("Open is not supported");
//            }
//        } catch (IOException exp) {
//            exp.printStackTrace();
//        }
        String osName = System.getProperty("os.name");
        try {
            if (osName.startsWith("Mac OS")) {
                Class<?> fileMgr = Class.forName("com.apple.eio.FileManager");
                Method openURL = fileMgr.getDeclaredMethod("openURL", new Class[] { String.class });
                openURL.invoke(null, new Object[] { url });
            } else if (osName.startsWith("Windows")) {
                Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
            } else // assume Unix or Linux
            {
                String[] browsers = { "firefox", "opera", "konqueror", "epiphany", "mozilla", "netscape" };
                String browser = null;
                for (int count = 0; count < browsers.length && browser == null; count++) {
                    if (Runtime.getRuntime().exec(new String[] { "which", browsers[count] }).waitFor() == 0) {
                        browser = browsers[count];
                    }
                }
                if (browser == null)
                    throw new Exception("Could not find web browser");
                else
                    Runtime.getRuntime().exec(new String[] { browser, url });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "errMsg" + ":\n" + e.getLocalizedMessage());
        }
        OAuth responseOAuth = threadService.login(oAuth);

        return responseOAuth;
    }

}

