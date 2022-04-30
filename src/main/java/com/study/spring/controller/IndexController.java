package com.study.spring.controller;

import com.study.spring.dto.OAuth;
import com.study.spring.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final MemberService memberService;
    private final ThreadService threadService;

    @RequestMapping("/")
    public String main() {
        return "index";
    }

    @RequestMapping("/success")
    public String success() {
        return "success";
    }

    /*
     *OAuth login ~ insert 까지
     * */

    @GetMapping("/oauth/{platform}/{UniqueNumber}")
    public String oAuth2(@PathVariable("UniqueNumber") String UniqueNumber,
                         @PathVariable("platform") String platform,
                         Model model) {
        model.addAttribute("UniqueNumber", UniqueNumber);
        model.addAttribute("platform", platform);
        return "oAuth2";
    }

    @ResponseBody
    @PostMapping("/oauth")
    public void oAuth2(@RequestBody OAuth oAuth) throws Exception {
        memberService.socialInsert(oAuth);
        threadService.loginNotify();
    }

}
