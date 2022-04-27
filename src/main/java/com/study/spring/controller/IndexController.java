package com.study.spring.controller;

import com.study.spring.config.auth.dto.OAuth;
import com.study.spring.domain.User;
import com.study.spring.dto.PostsResponseDto;
import com.study.spring.service.MemberService;
import com.study.spring.service.posts.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class IndexController {
    private final PostsService postsService;
    private final MemberService memberService;
    private final HttpSession httpSession;

    @RequestMapping("/")
    public String main(Model model) {
        model.addAttribute("posts", postsService.findAllDesc());
        User user = (User) httpSession.getAttribute("user");
        if (user != null) {
            model.addAttribute("userName", user.getDisplayName());
        }
        return "index";
    }

    @GetMapping("posts/update/{id}")
    public String postsUpdate(@PathVariable Long id,
                              Model model) {

        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("posts", dto);
        return "update";
    }

    @RequestMapping("/posts/save")
    public String postsSave() {
        return "postsSave";
    }

    @ResponseBody
    @DeleteMapping("/api/v1/posts/{id}")
    public void delete(@PathVariable Long id) {
        postsService.delete(id);
    }

    /*
    *
    * 경계선
    *
    * */

    @RequestMapping("/test/{userInfo}")
    public String oAuth2(@PathVariable("userInfo") String userInfo,
                         Model model) {
        model.addAttribute("user",userInfo);
        return "oAuth2";
    }

    @RequestMapping("/test2/{uid}/{user}")
    public void oAuth2(@PathVariable("uid") String uid,
                         @PathVariable("user") String userInfo) throws Exception {
        memberService.socialInsert(new OAuth(userInfo,uid));
    }

}
