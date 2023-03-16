package com.study.spring.controller;

import com.study.spring.service.CommonService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.*;

import java.util.Iterator;

@RestController
public class CommonController {

    private final CommonService commonService;

    public CommonController(CommonService commonService) {
        this.commonService = commonService;
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

    @PostMapping("/toIdToken")
    public String createJwt(@RequestParam("uid") String uid) throws Exception {
        return commonService.createJwt(uid);
    }

    @PostMapping("toUid")
    public String getJwt(@RequestParam("idToken") String idToken) throws Exception {
        return commonService.getJwt(idToken);
    }

    //    @PostMapping("/requestJwt")
    //    public String createJwt(@RequestParam("id") String id) {
    //        return memberService.createJwt(id);
    //    }
}
