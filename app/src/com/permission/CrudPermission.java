package com.permission;

import com.model.Entity;

public interface CrudPermission<E extends Entity> {
    boolean canAdd(E entity);
    boolean canUpdate(E entity);
    boolean canDelete(E entity);
    boolean canRead(E entity);
    boolean canRead(Integer id);
}
