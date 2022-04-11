package com.study.spring.domain.posts;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    @After
    public void cleanup() {
        postsRepository.deleteAll();
    }

    @Test
    public void 게시글저장_불러오기() {
        //given
        String title = "테스트 게시글";
        String content = "테스트 본문";

        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author("jojoldu@gmail.com")
                .build());

        //when
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }

    @Test
    public void base_time_entity_등록(){
        //given
        LocalDateTime now = LocalDateTime.of(2022,4,7,0,0,0);
        postsRepository.save(
                Posts.builder()
                        .title("test")
                        .author("test")
                        .content("test")
                        .build()
        );

        Posts postsList = postsRepository.findAll().get(0);

        System.out.println("생성날짜 : "+ postsList.getCreateDate() + " 수정날짜 : " + postsList.getModifiedDate());


    }
}
