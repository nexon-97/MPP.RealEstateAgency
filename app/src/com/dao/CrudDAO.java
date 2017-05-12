package com.dao;

import com.model.Entity;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;

import java.util.List;
import java.util.Map;

public interface CrudDAO<E> {
    E get(int id);
    boolean add(E entity);
    boolean delete(E entity);
    boolean update(E entity);
    List<E> list();
    List<E> filter(Criteria criteria);
    List<E> filter(SQLQuery query);
    List<E> filter(SQLQuery query, Map<String, Object> params, Map<String, Class<? extends Entity>> entities, Map<String, String> joins);
}
