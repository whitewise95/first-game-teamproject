package com.whitewise.web.dmain.Posts;

import com.whitewise.web.domain.posts.*;
import com.whitewise.web.dto.PostsUpdateRequestDto;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.*;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostRepositoryTest {

    RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private PostRepository postRepository;

    @After
    public void cleanup() {
        postRepository.deleteAll();
    }

    @Test
    public void 게시글저장_불러오기() {
        //given
        String title = "테스트게시글";
        String content = "테스트 본문";
        postRepository.save(
                Posts.builder()
                        .title(title)
                        .author("csss")
                        .content(content)
                        .build()
        );

        //when
        List<Posts> postsList = postRepository.findAll();

        //then
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }

    @Test
    public void 게시글업데이트() {
        //given
        String title = "테스트게시글";
        String content = "테스트 본문";
        Posts posts = postRepository.save(
                Posts.builder()
                        .title(title)
                        .author("csss")
                        .content(content)
                        .build()
        );

        Long updateId = posts.getId();
        String updateTitle = "업데이트합니다.";
        String updateContent = "업데이트하는 글";

        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
                .title(updateTitle)
                .content(updateContent)
                .build();

        String url = "http://locahost:8091/web/v1/posts/" + updateId;

        HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        //when
        ResponseEntity<Long> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                requestEntity,
                Long.class
        );

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all = postRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(updateTitle);
        assertThat(all.get(0).getContent()).isEqualTo(updateContent);

    }

}
