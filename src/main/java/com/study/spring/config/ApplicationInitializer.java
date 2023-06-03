package com.study.spring.config;

import com.study.spring.components.fireBase.FireBase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class ApplicationInitializer implements ApplicationRunner {

    private final FireBase fireBase;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        fireBase.init();
    }
}
