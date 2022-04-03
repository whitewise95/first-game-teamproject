package com.whitewise.web.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class TestController {

    @GetMapping("/hello2")
    public String hello(){
        return "hello";
    }
}
