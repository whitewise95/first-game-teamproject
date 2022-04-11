package com.study.spring.dto;

import com.study.spring.domain.posts.Posts;
import lombok.*;

@Getter
@Setter
public class PostsResponseDto {

    private Long id;
    private String title;
    private String content;
    private String author;

    @Builder
    public PostsResponseDto(Posts entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getAuthor();
    }
}
