package com.study.spring.controller;

import com.study.spring.config.auth.dto.OAuth;
import com.study.spring.domain.User;
import com.study.spring.service.MemberService;
import com.study.spring.service.common.CommonService;
import org.slf4j.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.*;

import javax.servlet.http.HttpSession;

import java.lang.reflect.Method;
import java.util.*;

@RestController
@RequestMapping("/member")
public class MemberController {

    private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

    private final HttpSession httpSession;
    private MemberService memberService;
    private CommonService commonService;

    public MemberController(HttpSession httpSession, MemberService memberService, CommonService commonService) {
        this.httpSession = httpSession;
        this.memberService = memberService;
        this.commonService = commonService;
    }

    //    @PostMapping("/requestJwt")
    //    public String createJwt(@RequestParam("id") String id) {
    //        return memberService.createJwt(id);
    //    }

    @PostMapping("/toIdToken")
    public String createJwt(@RequestParam("uid") String uid) throws Exception {
        return memberService.createJwt(uid);
    }

    @PostMapping("toUid")
    public String getJwt(@RequestParam("idToken") String idToken) throws Exception {
        return memberService.getJwt(idToken);
    }

    /*
     * 회원가입
     * 회원정보를 받아 insert
     * */
    @PostMapping("/insertMember")
    public MemberService.ResponseCode insertMember(User user) throws Exception {
        return memberService.insertMember(user);
    }

    /*
     * 로그인
     *  회원정보를 받아 select 후 토근 반환
     * */
    @PostMapping("/login")
    public MemberService.ResponseCode login(User user) throws Exception {
        return memberService.login(user);
    }

    /*
     *
     * 넘겨준 엑셀 파일을  MultipartHttpServletRequest 객체로 받음
     *  MultipartFile 객체로 변환
     * */
    @PostMapping("/fileUpload")
    public String uploadFile(MultipartHttpServletRequest request) {
        MultipartFile file = null;
        Iterator<String> iterator = request.getFileNames();
        if (iterator.hasNext()) {
            file = request.getFile(iterator.next());
        }
        commonService.fileUpload(file);
        return null;
    }

    /*
     * Oauth2 유저 값 세션 저장 후 창 띄어주기
     *
     * */
    @RequestMapping("/login/{platform}/{userInfo}")
    public String login(@PathVariable("platform") String platform,
                        @PathVariable("userInfo") String userInfo) throws Exception {
        String os = System.getProperty("os.name");
        Runtime runtime = Runtime.getRuntime();
        String url = "http://localhost:8091/test/"+userInfo;
        httpSession.setAttribute("","");
        if (os.startsWith("Windows")) {
            String cmd = "rundll32 url.dll,FileProtocolHandler " + url;
            Process p = runtime.exec(cmd);
        } else if (os.startsWith("Mac OS")) {
            Class fileMgr = Class.forName("com.apple.eio.FileManager");
            Method openURL = fileMgr.getDeclaredMethod("openURL", new Class[] { String.class });
            openURL.invoke(null, new Object[] { url });
        }
        OAuth oAuth = null;
        while(true){
            System.out.println("돌고있는중");
            oAuth = memberService.socialSelect(new OAuth(userInfo, userInfo));
            if(Optional.ofNullable(oAuth).isPresent()){
                break;
            }
        }
        //        User user = (User) httpSession.getAttribute("user");
        //        return createJwt(user.getUid());
        System.out.println(oAuth.getUid());
        System.out.println(oAuth.getUserInfo());
        return null;
    }

}

