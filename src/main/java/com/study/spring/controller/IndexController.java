package com.study.spring.controller;

import com.study.spring.config.auth.dto.SessionUser;
import com.study.spring.dto.PostsResponseDto;
import com.study.spring.service.posts.PostsService;
import lombok.RequiredArgsConstructor;
import org.h2.engine.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

import java.io.*;

@Controller
@RequiredArgsConstructor
public class IndexController {
    private final PostsService postsService;
    private final HttpSession httpSession;

    @RequestMapping("/")
    public String main(Model model) {
        model.addAttribute("posts", postsService.findAllDesc());
        SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if(user != null) {
            model.addAttribute("userName", user.getName());
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

    @GetMapping("/login/{oauth2}")
    public String login(@PathVariable String oauth2) {
        return "redirect:/oauth2/authorization/"+oauth2;
    }
}
