package com.whitewise.web.controller;

import com.whitewise.web.domain.posts.Posts;
import com.whitewise.web.dto.*;
import com.whitewise.web.model.HelloResponseDto;
import com.whitewise.web.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController("/web")
@RequiredArgsConstructor
public class WebController {

    private PostsService postsService;

    @PutMapping("/v1/posts/{id}")
    public Long update(@PathVariable Long id,
                       @RequestBody PostsUpdateRequestDto postsUpdateRequestDto) {

        return postsService.update(id, postsUpdateRequestDto);
    }

    @GetMapping("/v1/posts/{id}")
    public PostsResponseDto findByid(@PathVariable Long id) {
        return postsService.findById(id);
    }
}
