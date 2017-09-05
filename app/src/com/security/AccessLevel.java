package com.security;

import com.model.RoleId;

public enum AccessLevel {
    Any(0),
    Authorized(1),
    Admin(2);

    private int value;

    AccessLevel(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static int getValueForRole(RoleId roleId) {
        switch (roleId) {
            case User:
            case Broker:
            case Rieltor:
                return 1;
            case Admin:
                return 2;
            default:
                return 0;
        }
    }
}
