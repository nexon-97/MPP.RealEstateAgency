package com.services.impl;

import com.dao.PropertyDAO;
import com.model.Property;
import com.model.User;
import com.model.PropertyType;

import com.services.AuthService;
import com.services.PermissionService;
import com.services.PropertyService;
import com.services.shared.AbstractCrudService;
import com.services.shared.BaseService;
import com.utils.request.validator.RequestValidationChain;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public class PropertyServiceImpl extends AbstractCrudService<Property> implements PropertyService {

    @Autowired
    private AuthService authService;

    @Autowired
    private PermissionService permissionService;

    private PropertyDAO propertyDAO;


    @Autowired
    public PropertyServiceImpl(PropertyDAO propertyDAO) {
        super(propertyDAO);
        this.propertyDAO = propertyDAO;
    }

    @Override
    public boolean delete(Property property) {
        User loggedUser = authService.getLoggedUser();
        if (permissionService.canDeleteProperty(loggedUser, property)) {
            return super.delete(property);
        }

        return false;
    }

    @Override
    public List<Property> getPropertiesOwnedByUser(User user) {
        return propertyDAO.getPropertiesOwnedByUser(user);
    }
}
