package com.study.spring.controller;

import com.study.spring.domain.*;
import com.study.spring.domain.resultType.*;
import com.study.spring.dto.OAuth;
import com.study.spring.service.MemberService;
import com.study.spring.service.common.CommonService;
import org.apache.commons.codec.binary.StringUtils;
import org.slf4j.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public String login(@Validated(Login.class) OAuth oAuth) throws Exception {
        return threadService.login(oAuth).getUid();
    }

    @PostMapping("/nickName")
    public String nickName(@Validated(NickName.class) User user) throws Exception {
        if(StringUtils.equals(user.getNickName(), "guest")) {
            throw new RuntimeException("[guest]는 사용할 수 없는 닉네임입니다.");
        }
        return memberService.nickNameChange(user);
    }

}

