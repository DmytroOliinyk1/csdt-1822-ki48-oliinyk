package com.lpnu.virtual.library.core.user.model;

import java.util.Arrays;

public enum Roles {
    ADMIN,
    USER,
    ROLE_ANONYMOUS;

    public static Boolean contain(String authority) {
        return Arrays.stream(Roles.values()).anyMatch(r -> r.name().equals(authority));
    }
}
