package com.services.shared;

import com.dao.AbstractCrudDAO;
import com.dao.CrudDAO;
import com.model.Entity;
import com.model.User;
import com.services.CrudService;
import com.utils.request.ErrorRegistry;
import com.utils.request.ResponseData;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public abstract class AbstractCrudService<E extends Entity> extends BaseService implements CrudService<E>, ErrorRegistry {

    private Class<E> metaclass;
    private ResponseData responseData;

    public AbstractCrudService(ServiceId serviceId, ServiceSharedResources sharedResources, Class<E> metaclass) {
        super(serviceId, sharedResources);

        this.metaclass = metaclass;
        this.responseData = new ResponseData(HttpServletResponse.SC_OK, null);
    }

    @Override
    public boolean add(E entity) {
        if (canAdd(entity)) {
            CrudDAO<E> dao = new AbstractCrudDAO<>(this.metaclass);

            return dao.add(entity);
        }

        return false;
    }

    @Override
    public boolean update(E entity) {
        if (canUpdate(entity)) {
            CrudDAO<E> dao = new AbstractCrudDAO<>(this.metaclass);
            return dao.update(entity);
        }

        return false;
    }

    @Override
    public boolean delete(E entity) {
        if (canDelete(entity)) {
            CrudDAO<E> dao = new AbstractCrudDAO<>(this.metaclass);
            return dao.delete(entity);
        }

        return false;
    }

    @Override
    public E get(int id) {
        if (canRead(id)) {
            CrudDAO<E> dao = new AbstractCrudDAO<>(this.metaclass);
            return dao.get(id);
        }

        return null;
    }

    @Override
    public List<E> list() {
        CrudDAO<E> dao = new AbstractCrudDAO<>(this.metaclass);
        return dao.list();
    }

    @Override
    public boolean canAdd(E entity) {
        return true;
    }

    @Override
    public boolean canUpdate(E entity) {
        User loggedUser = getLoggedUser();
        return (loggedUser != null && entity.isOwner(loggedUser));
    }

    @Override
    public boolean canDelete(E entity) {
        User loggedUser = getLoggedUser();
        return (loggedUser != null && entity.isOwner(loggedUser));
    }

    @Override
    public boolean canRead(E entity) {
        return true;
    }

    @Override
    public boolean canRead(Integer id) {
        return id != null;
    }

    @Override
    public E constructFromRequest() {
        E entity;

        try {
            entity = this.metaclass.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
            return null;
        }

        try {
            String id = getSharedResources().getRequest().getParameter("id");
            entity.setId(Integer.valueOf(id));
        } catch (NullPointerException | NumberFormatException e) {
            e.printStackTrace();
        }

        return entity;
    }

    @Override
    public ResponseData getResponseData() {
        return this.responseData;
    }

    protected void setResponseData(ResponseData responseData) {
        this.responseData = responseData;
    }

    protected User getLoggedUser() {
        return ServiceManager.getInstance().getAuthService().getLoggedUser();
    }
}
