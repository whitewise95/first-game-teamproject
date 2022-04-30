package com.study.spring.service;

import com.study.spring.components.Components;
import com.study.spring.dto.OAuth;
import com.study.spring.domain.User;
import com.study.spring.dto.common.resultType.StatusCode;
import com.study.spring.mapper.MemberMapper;
import com.study.spring.service.common.CommonService;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;

import java.util.Optional;

@Service
public class MemberService {

    private static final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

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

    public void socialInsert(OAuth oAuth) throws Exception {
        memberMapper.socialInsert(oAuth);
    }

    public OAuth socialSelect(OAuth oAuth) throws Exception {
        return memberMapper.socialSelect(oAuth);
    }

    public SecretKeySpec getSecretKeySpec(byte[] secretKeyBytes) {
        return new SecretKeySpec(secretKeyBytes, signatureAlgorithm.getJcaName());
    }

    public static class ResponseCode {
        private int statusCode;
        private String message;
        private String token;
        private User user;

        public ResponseCode() {
        }

        public ResponseCode(StatusCode status, String message) {
            this.statusCode = status.getStatusCode();
            this.message = message;
        }

        public ResponseCode(StatusCode statusCode, String message, String token) {
            this.statusCode = statusCode.getStatusCode();
            this.message = message;
            this.token = token;
        }

        public int getStatusCode() {
            return statusCode;
        }

        public void setStatusCode(int status) {
            this.statusCode = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

    }

}
