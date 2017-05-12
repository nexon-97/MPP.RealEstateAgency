package com.services;

import com.model.Entity;

import java.util.List;

public interface CrudService<E extends Entity> {
    boolean add(E entity);
    boolean update(E entity);
    boolean delete(E entity);
    E get(int id);
    List<E> list();
}
