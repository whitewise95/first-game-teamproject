package com.study.spring.controller;

import com.study.spring.domain.User;
import com.study.spring.domain.resultType.Update;
import com.study.spring.service.StatisticService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/statistic")
public class StatisticController {

    private final StatisticService abilityService;

    public StatisticController(StatisticService abilityService) {
        this.abilityService = abilityService;
    }

    @PostMapping("/default")
    public String defaultChange(@Validated(Update.class) User user) {
        return abilityService.defaultChange(user);
    }
}
