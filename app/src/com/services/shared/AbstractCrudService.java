package com.services.shared;

import com.dao.AbstractCrudDAO;
import com.dao.CrudDAO;
import com.model.Entity;
import com.services.CrudService;

import java.util.List;

public abstract class AbstractCrudService<E extends Entity> extends BaseService implements CrudService<E> {

    private Class<E> metaclass;

    public AbstractCrudService(Class<E> metaclass) {
        this.metaclass = metaclass;
    }

    @Override
    public boolean add(E entity) {
        CrudDAO<E> dao = new AbstractCrudDAO<>(this.metaclass);
        return dao.add(entity);
    }

    @Override
    public boolean update(E entity) {
        CrudDAO<E> dao = new AbstractCrudDAO<>(this.metaclass);
        return dao.update(entity);
    }

    @Override
    public boolean delete(E entity) {
        CrudDAO<E> dao = new AbstractCrudDAO<>(this.metaclass);
        return dao.delete(entity);
    }

    @Override
    public E get(int id) {
        CrudDAO<E> dao = new AbstractCrudDAO<>(this.metaclass);
        return dao.get(id);
    }

    @Override
    public List<E> list() {
        CrudDAO<E> dao = new AbstractCrudDAO<>(this.metaclass);
        return dao.list();
    }
}
