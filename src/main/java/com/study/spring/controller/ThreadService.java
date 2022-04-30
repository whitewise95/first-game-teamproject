package com.study.spring.controller;

import com.study.spring.dto.OAuth;
import com.study.spring.mapper.MemberMapper;
import com.study.spring.service.common.CommonService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ThreadService extends Thread {

    private final MemberMapper memberMapper;
    private final CommonService commonService;

    public ThreadService(MemberMapper memberMapper, CommonService commonService) {
        this.memberMapper = memberMapper;
        this.commonService = commonService;
    }

    public synchronized OAuth login(OAuth oAuth) throws Exception {
        long beforeTime = System.currentTimeMillis();
        long afterTime = 0;
        OAuth responseOAuth = null;

        while (true) {
            afterTime = System.currentTimeMillis();
            if ((afterTime - beforeTime) / 1000 > 300) {
                throw new RuntimeException("시간이 만료되었습니다.");
            }

            wait();
            sleep(5000);

            responseOAuth = memberMapper.socialSelect(oAuth);
            if (Optional.ofNullable(responseOAuth).isPresent()) {
                responseOAuth.setToken(commonService.createJwt(responseOAuth.getUid()));
                break;
            }
        }
        return responseOAuth;
    }

    public synchronized void loginNotify() {
        notifyAll();
    }

}
