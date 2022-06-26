package com.study.spring.controller;

import com.study.spring.components.*;
import com.study.spring.domain.User;
import com.study.spring.dto.common.resultType.Platform;
import com.study.spring.service.*;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class IndexController {

    private final MemberService memberService;
    private final Egoism egoism;

    public IndexController(MemberService memberService,
                           Components components) {
        this.memberService = memberService;
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
        if (!StringUtils.equals(platform, Platform.GOOGLE.getPlatform()) && !StringUtils.equals(platform, Platform.FACEBOOK.getPlatform())) {
            throw new RuntimeException("플랫폼이 잘못되었습니니다. platform : " + platform);
        }
        model.addAttribute("UniqueNumber", UniqueNumber);
        model.addAttribute("platform", platform);
        model.addAttribute("url", egoism.getBaseUrl());
        return "oAuth2";
    }

    @ResponseBody
    @PostMapping("/oauth")
    public void oAuth2(@RequestBody RequestUserOauth requestUserOauth) {
        memberService.socialInsert(requestUserOauth);
    }

    public static class RequestUserOauth extends User {
        private String uniqueNumber;
        private String platform;

        public String getUniqueNumber() {
            return uniqueNumber;
        }

        public RequestUserOauth setUniqueNumber(String uniqueNumber) {
            this.uniqueNumber = uniqueNumber;
            return this;
        }

        public String getPlatform() {
            return platform;
        }

        public RequestUserOauth setPlatform(String platform) {
            this.platform = platform;
            return this;
        }
    }

}
