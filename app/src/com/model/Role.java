package com.model;

import java.util.Set;

public class Role {
    private int id;
    private String name;
    private Set<Permission> permissions;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Set<Permission> getPermissions() {
        return this.permissions;
    }

    public RoleId getRoleId() {
        try {
            RoleId roleId = RoleId.values()[this.id];
            return roleId;
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!this.getClass().equals(obj.getClass())) return false;

        Role obj2 = (Role)obj;
        return ((this.id == obj2.getId()) && (this.name.equals(obj2.getName())));
    }

    public int hashCode() {
        return (id + name).hashCode();
    }

    public String toString() {
        return "lol";
    }
}
