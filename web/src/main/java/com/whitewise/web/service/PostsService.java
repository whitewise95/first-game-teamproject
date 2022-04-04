package com.whitewise.web.service;

import com.whitewise.web.domain.posts.*;
import com.whitewise.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostsService {

    private PostRepository postRepository;

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto postsUpdateRequestDto) {
        Posts posts = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당게시글이 없습니다. id = " + id));

        posts.update(postsUpdateRequestDto.getTitle(), postsUpdateRequestDto.getContent());

        return id;
    }

    public PostsResponseDto findById(Long id) {
        Posts entity = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당게시물이 없습니다. id= " + id));
        return new PostsResponseDto(entity);
    }
}
