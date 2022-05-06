package com.study.spring.controller;

import com.study.spring.components.*;
import com.study.spring.domain.User;
import com.study.spring.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class IndexController {

    private final MemberService memberService;
    private final ThreadService threadService;
    private final Egoism egoism;

    public IndexController(MemberService memberService, ThreadService threadService, Components components) {
        this.memberService = memberService;
        this.threadService = threadService;
        this.egoism = components.getEgoism();
    }

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
        model.addAttribute("url", egoism.getBaseUrl());
        return "oAuth2";
    }

    @ResponseBody
    @PostMapping("/oauth")
    public void oAuth2(@RequestBody User user) throws Exception {
        memberService.socialInsert(user);
        threadService.loginNotify();
    }

}
