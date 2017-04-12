package com.model;

public class Permission {
    private int id;
    private String name;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!this.getClass().equals(obj.getClass())) return false;

        Permission obj2 = (Permission)obj;
        return (this.id == obj2.getId());
    }

    public int hashCode() {
        return (id + name).hashCode();
    }
}
