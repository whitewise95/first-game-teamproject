package com.study.spring.controller;

import com.study.spring.domain.User;
import com.study.spring.domain.resultType.*;
import com.study.spring.dto.OAuth;
import com.study.spring.service.*;
import com.study.spring.service.CommonService;
import org.slf4j.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
public class MemberController {

    private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

    private final MemberService memberService;
    private final CommonService commonService;

    public MemberController(MemberService memberService,
                            CommonService commonService) {
        this.memberService = memberService;
        this.commonService = commonService;
    }

    @PostMapping("/login")
    public String login(@Validated(Login.class) OAuth oAuth) {
         return memberService.login(oAuth);
    }

    @PostMapping("/nickNameChange")
    public String nickNameChange(@Validated(NickName.class) User user) {
        return memberService.nickNameChange(user);
    }

    @PostMapping("/userInfo")
    public User userInfo(@Validated(Login.class) User user) {
        return memberService.userinfo(user);
    }

    @PostMapping("/guestSelect")
    public User guestGuest(@Validated(Login.class) User user) {
        return memberService.guestSelect(user);
    }
}


