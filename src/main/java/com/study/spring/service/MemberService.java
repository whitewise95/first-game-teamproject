package com.study.spring.service;

import com.study.spring.components.Components;
import com.study.spring.controller.IndexController;
import com.study.spring.domain.User;
import com.study.spring.dto.OAuth;
import com.study.spring.mapper.MemberMapper;
import com.study.spring.service.common.CommonService;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;

import java.util.Optional;

@Service
public class MemberService {

    private static final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    private static final String GUEST = "guest";

    private final Components components;
    private final MemberMapper memberMapper;
    private final CommonService commonService;

    public MemberService(Components components,
                         MemberMapper memberMapper,
                         CommonService commonService) {
        this.components = components;
        this.memberMapper = memberMapper;
        this.commonService = commonService;
    }

    public void socialInsert(IndexController.RequestUserOauth requestUserOauth) throws Exception {
        OAuth oAuth = OAuth.builder()
                .uniqueNumber(requestUserOauth.getUniqueNumber())
                .uid(requestUserOauth.getUid())
                .build();

        User user = User.builder()
                .uid(requestUserOauth.getUid())
                .nickName(GUEST)
                .email(requestUserOauth.getEmail())
                .build();

        memberMapper.socialInsert(oAuth);
        memberMapper.userInsert(user);
    }

    public OAuth socialSelect(OAuth oAuth) throws Exception {
        return memberMapper.socialSelect(oAuth);
    }

    public String nickNameChange(User user) throws Exception {
        User selectUser = Optional.ofNullable(memberMapper.userSelect(user.getUid()))
                .orElseThrow(() -> new NullPointerException("유저를 찾지 못했습니다. uid :" + user.getUid()));
        memberMapper.nickNameChange(selectUser);
        return "저장완료";
    }

    public SecretKeySpec getSecretKeySpec(byte[] secretKeyBytes) {
        return new SecretKeySpec(secretKeyBytes, signatureAlgorithm.getJcaName());
    }
}
