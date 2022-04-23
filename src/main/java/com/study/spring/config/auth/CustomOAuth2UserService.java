package com.study.spring.config.auth;

import com.study.spring.config.auth.dto.OAuthAttributes;
import com.study.spring.domain.User;
import com.study.spring.domain.user.*;
import com.study.spring.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.*;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.*;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.*;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService  {
    private static final Logger logger = LoggerFactory.getLogger(CustomOAuth2UserService.class);

    private final UserRepository userRepository;
    private final MemberMapper memberMapper;
    private final HttpSession httpSession;
    private final AppProperties appProperties;

//    @Override
//    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
//        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);
//
//        try {
//            return processOAuth2User(oAuth2UserRequest, oAuth2User);
//        } catch (AuthenticationException e) {
//            throw e;
//        } catch (Exception e) {
//            throw new InternalAuthenticationServiceException(e.getMessage(), e.getCause());
//        }
//    }
//
//    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
//        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
//
//        logger.info("OAuth2UserInfo : {}", oAuth2UserInfo.getEmail());
//        validateEmail(oAuth2UserInfo);
//
//        Optional<Member> memberOptionalEmail = memberRepository.findByEmail(oAuth2UserInfo.getEmail());
//
//        Member member;
//        if (memberOptionalEmail.isPresent()) {
//            member = memberOptionalEmail.get();
//            if (!member.getProvider().equals(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))) {
//                throw new OAuth2AuthenticationProcessingException("이미 등록된 회원입니다.");
//            }
//            member = updateExistingMember(member, oAuth2UserInfo);
//        } else {
//            member = registerNewMember(oAuth2UserRequest, oAuth2UserInfo);
//        }
//
//        return UserPrincipal.create(member, oAuth2User.getAttributes());
//    }
//
//    private Member registerNewMember(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
//        AuthProvider authProvider = AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId());
//
//        Member member = Member.builder()
//                .name(oAuth2UserInfo.getName())
//                .email(oAuth2UserInfo.getEmail())
//                .provider(authProvider)
//                .providerId(oAuth2UserInfo.getId())
//                .imageUrl(oAuth2UserInfo.getImageUrl())
//                .role(Role.ROLE_MEMBER)
//                .build();
//
//        return memberRepository.save(member);
//    }
//
//    private Member updateExistingMember(Member member, OAuth2UserInfo oAuth2UserInfo) {
//        member.updateExistingMember(oAuth2UserInfo.getName(), oAuth2UserInfo.getImageUrl());
//        return memberRepository.save(member);
//    }
//
//    private void validateEmail(OAuth2UserInfo oAuth2UserInfo) {
//        if (StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
//            throw new OAuth2AuthenticationProcessingException("OAuth2 Provider에서 이메일을 찾을 수 없습니다.");
//        }
//    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        User user = null;

        try {
            user = memberMapper.socialInsert(attributes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        httpSession.setAttribute("user", user);

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(Role.USER.name())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

}
