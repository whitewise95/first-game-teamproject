package com.study.spring.controller;

import com.study.spring.dto.OAuth;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.*;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MemberControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @Test
    public void mainTest() {

        String platform = "goog";
        String uniqueNumber = "uniqueNumber";

        String url = "http://localhost:" + port + "/member/login";

        OAuth oAuth = new OAuth();
        oAuth.setPlatform(platform);
        oAuth.setUniqueNumber(uniqueNumber);


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        HttpEntity request = new HttpEntity(headers);
        HttpEntity<OAuth> oAuthEntity = new HttpEntity<>(oAuth);

        //when
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.POST,
                oAuthEntity,
                String.class
        );

    }
}
