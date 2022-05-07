package com.study.spring.service;

import com.study.spring.components.Components;
import com.study.spring.controller.IndexController;
import com.study.spring.domain.User;
import com.study.spring.dto.OAuth;
import com.study.spring.mapper.MemberMapper;
import com.study.spring.service.common.CommonService;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.codec.binary.StringUtils;
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
                .nickName(null)
                .email(requestUserOauth.getEmail())
                .build();

        memberMapper.socialInsert(oAuth);
        memberMapper.userInsert(user);
    }

    //사용아직안하는중
    public OAuth socialSelect(OAuth oAuth) throws Exception {
        return memberMapper.socialSelect(oAuth);
    }

    public String nickNameChange(User user) throws Exception {
        User selectUser = Optional.ofNullable(memberMapper.userSelect(user.getUid()))
                .orElseThrow(() -> new NullPointerException(String.format("uid : %s 에 대한 정보를 찾지못했습니다.", user.getUid())));
        if (StringUtils.equals(selectUser.getNickName(), user.getNickName())) {
            throw new RuntimeException("변경된 사항이 없습니다.");
        }
        if (memberMapper.nickNameSelect(user.getNickName()) > 0) {
            throw new RuntimeException(String.format("%s 는 이미 사용중입니다.", user.getNickName()));
        }
        return memberMapper.nickNameChange(user);
    }

    public User userinfo(User user) {
        return Optional.ofNullable(memberMapper.userSelect(user.getUid()))
                .orElseThrow(() -> new RuntimeException(String.format("uid : %s 에 대한 정보를 찾지못했습니다.", user.getUid())));
    }

    public SecretKeySpec getSecretKeySpec(byte[] secretKeyBytes) {
        return new SecretKeySpec(secretKeyBytes, signatureAlgorithm.getJcaName());
    }
}
