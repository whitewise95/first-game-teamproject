package com.whitewise.web.dto;

import com.whitewise.web.domain.posts.Posts;
import lombok.*;

@Getter
@NoArgsConstructor
public class PostsUpdateRequestDto {

    private Long id;
    private String title;
    private String content;
    private String author;

    @Builder
    public PostsUpdateRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
