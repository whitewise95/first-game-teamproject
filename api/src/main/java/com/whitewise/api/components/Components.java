package com.whitewise.api.components;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Components {

    private DefaultData defaultData;

    public DefaultData getDefaultData() {
        return defaultData;
    }

    public Components setDefaultData(DefaultData defaultData) {
        this.defaultData = defaultData;
        return this;
    }

    public BCryptPasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
