package com.services.shared;

import com.dao.CrudDAO;
import com.model.Entity;
import com.services.CrudService;

import java.util.List;

public abstract class AbstractCrudService<E extends Entity> extends BaseService implements CrudService<E> {

    private CrudDAO<E> dao;

    public AbstractCrudService(CrudDAO<E> dao) {
        this.dao = dao;
    }

    @Override
    public boolean add(E entity) {
        return dao.add(entity);
    }

    @Override
    public boolean update(E entity) {
        return dao.update(entity);
    }

    @Override
    public boolean delete(E entity) {
        return dao.delete(entity);
    }

    @Override
    public E get(int id) {
        return dao.get(id);
    }

    @Override
    public List<E> list() {
        return dao.list();
    }
}
