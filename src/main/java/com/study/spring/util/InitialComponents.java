package com.study.spring.util;

import com.study.spring.components.Components;
import com.study.spring.dto.common.resultType.ApplicationProfile;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;

import java.util.*;

@Configuration
public class InitialComponents implements EnvironmentAware {

    private Environment environment;

    private List<ApplicationProfile> PRODUCTION_PROFILES = Arrays.asList(ApplicationProfile.DEFAULT, ApplicationProfile.LOCAL);

    @Bean
    @ConfigurationProperties(prefix = "components")
    public Components components() {
        return new Components();
    }

//    @Bean
//    public MemberMapper memberMapper() {
//        if (PRODUCTION_PROFILES.stream().map(ApplicationProfile::getName).anyMatch(requiredProfile -> environment.acceptsProfiles(requiredProfile))) {
//            return new DefaultMemberMapper();
//        }
//        return new EmptyMemberMapper();
//    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
