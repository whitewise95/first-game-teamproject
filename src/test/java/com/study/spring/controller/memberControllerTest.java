package com.study.spring.controller;

import com.study.spring.dto.OAuth;
import com.study.spring.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Method;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class memberControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MemberRepository memberRepository;

//    @Test
//    public void 소설로그인() throws Exception {
//
//        String whitewise = "8oQVMsCKgfWTrHNWx8qRBOt7Nv53";
//        String platform = "google";
//        String uniqueNumber = "uniqueNumber1";
//        String os = System.getProperty("os.name");
//        Runtime runtime = Runtime.getRuntime();
//        String url = "http://localhost:8091/oauth/" + platform + "/" + uniqueNumber;
//        if (os.startsWith("Windows")) {
//            String cmd = "rundll32 url.dll,FileProtocolHandler " + url;
//            Process p = runtime.exec(cmd);
//        } else if (os.startsWith("Mac OS")) {
//            Class fileMgr = Class.forName("com.apple.eio.FileManager");
//            Method openURL = fileMgr.getDeclaredMethod("openURL", new Class[] { String.class });
//            openURL.invoke(null, new Object[] { url });
//        }
//
//        OAuth.OAuthBuilder oAuth = OAuth.builder().uniqueNumber(uniqueNumber);
//        HttpEntity<OAuth.OAuthBuilder> oAuthHttpEntity = new HttpEntity<>(oAuth);
//        String url2 = "http://localhost:" + 8091 + "/member/login";
//
//        //when
//        ResponseEntity<String> responseUid = restTemplate.exchange(
//                url2,
//                HttpMethod.POST,
//                oAuthHttpEntity,
//                String.class
//        );
//
//        //then
//        assertThat(responseUid.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(responseUid.getBody()).isEqualTo(whitewise);
//
//    }
}
