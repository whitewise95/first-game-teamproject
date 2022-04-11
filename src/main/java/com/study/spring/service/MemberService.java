package com.study.spring.service;

import com.google.firebase.auth.UserRecord;
import com.google.gson.Gson;
import com.study.spring.components.Components;
import com.study.spring.domain.User;
import com.study.spring.domain.common.resultType.StatusCode;
import com.study.spring.mapper.MemberMapper;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import java.util.*;

@Service
public class MemberService {

    private static final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    private Components components;
    private MemberMapper memberMapper;

    public MemberService(Components components, MemberMapper memberMapper) {
        this.components = components;
        this.memberMapper = memberMapper;
    }

    public ResponseCode insertMember(User user) throws Exception {
        System.out.println(components.getDefaultData().getSecretKey());
        //아이디 중복 확인
        if (!Optional.ofNullable(memberMapper.findOverLaPUserId(user).getEmail()).isPresent()) {
            //회원가입 진행
            user.setPassword(components.getPasswordEncoder().encode(user.getPassword()));
            memberMapper.insertMember(user);
            return new ResponseCode(StatusCode.OK, "Completion of membership registration");
        }
        return new ResponseCode(StatusCode.DUPLICATE, "Duplicate ID");
    }

    public ResponseCode login(User user) throws Exception {
        User selectUser = memberMapper.findOverLaPUserId(user);

        if (!Optional.ofNullable(selectUser.getEmail()).isPresent()) {
            return new ResponseCode(StatusCode.NO_CONTENT, "User information was not found");
        }

        if (!components.getPasswordEncoder().matches(user.getPassword(), selectUser.getPassword())) {
            return new ResponseCode(StatusCode.NO_CONTENT, "The passwords do not match");
        }

        //        new User().setEmail(userRecord.getEmail()).setDisplayName(userRecord.getDisplayName());
        return new ResponseCode(
                StatusCode.OK,
                "status.OK 200 ",
                createJwt(selectUser.getEmail())
        );
    }

    public String socialLogin(String uid) throws Exception {
        Map<String, Object> responseObject = new HashMap<>();

        UserRecord userRecord = memberMapper.socialLogin(uid);
        responseObject.put(
                "responseCode",
                new ResponseCode(
                        StatusCode.OK,
                        "status.OK 200 ",
                        createJwt(userRecord.getEmail())
                )
        );

        responseObject.put("user", new User().setEmail(userRecord.getEmail()));

        return new Gson().toJson(responseObject);
    }

    public String createJwt(String id) {
        return Jwts.builder()
                .setSubject(id)
                .signWith(signatureAlgorithm, getSecretKeySpec(DatatypeConverter.parseBase64Binary(components.getDefaultData().getSecretKey())))
                .setExpiration(new Date(System.currentTimeMillis() + 2 * 1000 * 60))
                .compact();
    }

    public String getJwt(String jwt) {
        try {
            return Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary("secretKey"))
                    .parseClaimsJws(jwt)
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            throw new RuntimeException("parseError : " + e.getMessage());
        }
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
