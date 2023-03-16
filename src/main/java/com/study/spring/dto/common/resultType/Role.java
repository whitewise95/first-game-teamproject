package com.study.spring.dto.common.resultType;

import static com.study.spring.dto.common.resultType.Role.Const.*;

public enum Role {
    GUEST(GUEST_ROLE),
    FAMILY(FAMILY_ROLE);

    private String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public Role setRole(String role) {
        this.role = role;
        return this;
    }

    public static class Const {
        public static final String GUEST_ROLE = "guest";
        public static final String FAMILY_ROLE = "family";
    }
}
