package com.study.spring.config.auth;

import com.study.spring.config.auth.repository.HttpCookieOAuth2AuthorizationRequestRepository;
import com.study.spring.domain.User;
import com.study.spring.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.IOException;
import java.util.Optional;

import static com.study.spring.config.auth.repository.HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;
    private final HttpSession httpSession;
    private final MemberService memberService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                                  HttpServletResponse response,
                                                  Authentication authentication) {
        String targetUrl = null;
        try {
            targetUrl = determineTargetUrl(request);
            if (response.isCommitted()) {
                logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            }

            clearAuthenticationAttributes(request, response);
            getRedirectStrategy().sendRedirect(request, response, targetUrl);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String determineTargetUrl(HttpServletRequest request) throws Exception {
        Optional<String> redirectUri = CookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME).map(Cookie::getValue);

        if (!redirectUri.isPresent()) {
            throw new RuntimeException("인증되지 않은 REDIRECT_URI입니다.");
        }

        String targetUri = redirectUri.orElse(getDefaultTargetUrl());
        User user = (User) httpSession.getAttribute("user");

        return UriComponentsBuilder.fromUriString(targetUri)
                .queryParam("accessToken", memberService.createJwt(user.getUid()))
                .build().toUriString();
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }
}