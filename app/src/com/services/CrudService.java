package com.services;

import com.model.Entity;
import com.permission.CrudPermission;
import com.utils.request.ErrorRegistry;

import java.util.List;

public interface CrudService<E extends Entity> extends CrudPermission<E>, ErrorRegistry {
    boolean add(E entity);
    boolean update(E entity);
    boolean delete(E entity);
    E get(int id);
    List<E> list();
    E constructFromRequest();
}
