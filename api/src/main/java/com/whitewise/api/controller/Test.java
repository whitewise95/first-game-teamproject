package com.whitewise.api.controller;

import com.whitewise.api.domain.common.resultType.ApplicationProfile;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

import java.io.IOException;
import java.util.*;

import static com.whitewise.api.domain.common.resultType.ApplicationProfile.*;

public class Test  implements EnvironmentAware {

    static Environment environment;

    public static void main(String[] args) throws IOException {


        List<ApplicationProfile> PRODUCTION_PROFILES = Arrays.asList(DEFAULT, LOCAL);

        PRODUCTION_PROFILES.stream().map(ApplicationProfile::getName).anyMatch(s -> {
            System.out.println(s);
            return environment.acceptsProfiles(s);
        });

    }

    @Override public void setEnvironment(Environment environment) {

    }
}
