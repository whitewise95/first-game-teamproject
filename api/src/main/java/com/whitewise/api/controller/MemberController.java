package com.whitewise.api.controller;

import com.whitewise.api.domain.User;
import com.whitewise.api.service.MemberService;
import org.slf4j.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
public class MemberController {
//
//    private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
//
//    private MemberService memberService;
//
//    public MemberController(MemberService memberService) {
//        this.memberService = memberService;
//    }
//
//    @PostMapping("/requestJwt")
//    public String createJwt(@RequestParam("id") String id) {
//        return memberService.createJwt(id);
//    }
//
//    @PostMapping("responseJwt")
//    public String getJwt(@RequestParam("jwt") String jwt) {
//        return memberService.getJwt(jwt);
//    }
//
//    /*
//     * 회원가입
//     * 회원정보를 받아 insert
//     * */
//    @PostMapping("/insertMember")
//    public MemberService.ResponseCode insertMember(User user) throws Exception {
//        return memberService.insertMember(user);
//    }
//
//    /*
//     * 로그인
//     *  회원정보를 받아 select 후 토근 반환
//     * */
//    @PostMapping("/login")
//    public MemberService.ResponseCode login(User user) throws Exception {
//        return memberService.login(user);
//    }
//
//    /*
//     *
//     * 소셜 로그인
//     *
//     * */
//    @PostMapping("/socialLogin")
//    public String socialLogin(String uid) throws Exception {
//        return memberService.socialLogin(uid);
//    }

}
