package com.study.spring.controller;

import com.study.spring.repository.MemberRepository;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class memberControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private MemberRepository memberRepository;
}
