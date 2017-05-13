package com.model;

public class Entity {

    protected int id;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isOwner(User user) {
        return false;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Entity) {
            Entity otherEntity = (Entity)other;
            return otherEntity.getId() == getId();
        }

        return false;
    }
}
