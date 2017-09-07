package com.security;

import com.model.RoleId;

public enum AccessLevel {
    Any(0),
    Authorized(1),
    Management(2),
    Admin(3);

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
                return 1;
            case Broker:
            case Realtor:
                return 2;
            case Admin:
                return 3;
            default:
                return 0;
        }
    }
}
