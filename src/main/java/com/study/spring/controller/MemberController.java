package com.study.spring.controller;

import com.study.spring.domain.User;
import com.study.spring.service.common.CommonService;
import com.study.spring.service.MemberService;
import org.slf4j.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.*;

import java.util.Iterator;

@RestController
@RequestMapping("/member")
public class MemberController {

    private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

    private MemberService memberService;
    private CommonService commonService;

    public MemberController(MemberService memberService, CommonService commonService) {
        this.memberService = memberService;
        this.commonService = commonService;
    }

    @PostMapping("/requestJwt")
    public String createJwt(@RequestParam("id") String id) {
        return memberService.createJwt(id);
    }

    @PostMapping("responseJwt")
    public String getJwt(@RequestParam("jwt") String jwt) {
        return memberService.getJwt(jwt);
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
     * 소셜 로그인
     *
     * */
    @PostMapping("/socialLogin")
    public String socialLogin(String uid) throws Exception {
        return memberService.socialLogin(uid);
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

}

