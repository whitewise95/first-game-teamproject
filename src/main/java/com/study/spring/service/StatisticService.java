package com.study.spring.service;

import com.study.spring.domain.User;
import com.study.spring.mapper.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StatisticService {

    private final StatisticMapper abilityMapper;
    private final MemberMapper memberMapper;

    public StatisticService(StatisticMapper abilityMapper, MemberMapper memberMapper) {
        this.abilityMapper = abilityMapper;
        this.memberMapper = memberMapper;
    }

    public String defaultChange(User user) {
        Optional.ofNullable(memberMapper.userSelect(user.getUid()))
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
