package com.study.spring.service;

import com.study.spring.domain.User;
import com.study.spring.repository.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StatisticService {

    private final StatisticRepository abilityMapper;
    private final MemberRepository memberRepository;

    public StatisticService(StatisticRepository abilityMapper, MemberRepository memberRepository) {
        this.abilityMapper = abilityMapper;
        this.memberRepository = memberRepository;
    }

    public String defaultChange(User user) {
        Optional.ofNullable(memberRepository.userSelect(user.getUid()))
                .ifPresent(selectUser -> {
                    Map<String, Object> updateUserInfo = new HashMap<>();
                    updateUserInfo.put("uid", user.getUid());
                    if (selectUser.getExp() != user.getExp()) {
                        updateUserInfo.put("exp", user.getExp());
                    }
                    if (selectUser.getGold() != user.getGold()) {
                        updateUserInfo.put("gold", user.getGold());
                    }
                    if (selectUser.getLevel() != user.getLevel()) {
                        updateUserInfo.put("level", user.getLevel());
                    }
                    if (updateUserInfo.size() == 1) {
                        return;
                    }
                    abilityMapper.defaultChange(updateUserInfo);
                });
        return user.getUid();
    }
}
