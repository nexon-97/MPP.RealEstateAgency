package com.permission;

public class EntityOperationDesc {

    private Operation operation;

    public EntityOperationDesc(EntityOperation entityOperation) {
        this.operation = entityOperation.type();
    }

    public Operation getOperation() {
        return this.operation;
    }
}
