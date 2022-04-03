package com.whitewise.api.domain.common.resultType;

import org.apache.commons.codec.binary.StringUtils;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.*;

import static com.whitewise.api.domain.common.resultType.ApplicationProfile.Const.*;

public enum ApplicationProfile {
    DEFAULT(DEFAULT_PROFILE_NAME),
    LOCAL(LOCAL_PROFILE_NAME),
    DEVELOPMENT(DEVELOP_PROFILE_NAME),
    STAGING(STAGING_PROFILE_NAME),
    PRODUCTION(PRODUCTION_PROFILE_NAME);

    private final String name;

    ApplicationProfile(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static final Predicate<String> APPLICATION_PROFILES_FILTER = profile ->
            StringUtils.equals(profile, DEFAULT_PROFILE_NAME) ||
                    Stream.of(ApplicationProfile.values())
                            .map(ApplicationProfile::getName)
                            .anyMatch(applicationProfile -> StringUtils.equals(profile, applicationProfile));

    public static final Predicate<String> NONE_APPLICATION_PROFILES_FILTER = profile ->
            !StringUtils.equals(profile, DEFAULT_PROFILE_NAME) &&
                    Stream.of(ApplicationProfile.values())
                            .map(ApplicationProfile::getName)
                            .noneMatch(applicationProfile -> StringUtils.equals(profile, applicationProfile));

    private static List<String> getAllProfiles(ConfigurableEnvironment environment) {
        return Stream.concat(
                Stream.of(environment.getDefaultProfiles()),
                Stream.of(environment.getActiveProfiles())
        ).collect(Collectors.toList());
    }

    public static List<String> findApplicationProfiles(ConfigurableEnvironment environment) {
        return getAllProfiles(environment).stream()
                .filter(APPLICATION_PROFILES_FILTER)
                .collect(Collectors.toList());
    }

    public static boolean isApplicationProfile(String profile) {
        return Arrays.stream(values()).anyMatch(applicationProfile -> StringUtils.equals(profile, applicationProfile.getName()));
    }

    public static List<String> findNoneApplicationProfiles(ConfigurableEnvironment environment) {
        return getAllProfiles(environment).stream()
                .filter(NONE_APPLICATION_PROFILES_FILTER)
                .collect(Collectors.toList());
    }

    public static class Const {
        public static final String DEFAULT_PROFILE_NAME = "default";
        public static final String LOCAL_PROFILE_NAME = "local";
        public static final String DEVELOP_PROFILE_NAME = "dev";
        public static final String STAGING_PROFILE_NAME = "stg";
        public static final String PRODUCTION_PROFILE_NAME = "prd";
    }
}
