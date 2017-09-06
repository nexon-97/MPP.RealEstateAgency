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

    /*@Override
    public boolean addProperty(RequestValidationChain requestValidationChain) {
        Property property = new Property();

        property.setType((PropertyType)requestValidationChain.getValue("type"));
        property.setCity((String)requestValidationChain.getValue("city"));
        property.setStreet((String)requestValidationChain.getValue("street"));
        property.setHouseNumber((Integer) requestValidationChain.getValue("houseNumber"));
        property.setBlockNumber((Integer) requestValidationChain.getValue("blockNumber"));
        property.setFlatNumber((Integer) requestValidationChain.getValue("flatNumber"));
        property.setRoomsCount((Integer) requestValidationChain.getValue("roomsCount"));
        property.setArea((Integer) requestValidationChain.getValue("area"));
        property.setDistanceToSubway((Integer) requestValidationChain.getValue("subway"));
        property.setDistanceToTransportStop((Integer) requestValidationChain.getValue("bus"));
        property.setHasFurniture((Boolean) requestValidationChain.getValue("furniture"));
        property.setHasInternet((Boolean) requestValidationChain.getValue("internet"));
        property.setHasTv((Boolean) requestValidationChain.getValue("tv"));
        property.setHasPhone((Boolean) requestValidationChain.getValue("phone"));
        property.setHasFridge((Boolean) requestValidationChain.getValue("fridge"));
        property.setHasStove((Boolean) requestValidationChain.getValue("stove"));
        property.setDescription((String)requestValidationChain.getValue("description"));

        User loggedUser = authService.getLoggedUser();
        property.setOwner(loggedUser);

        return propertyDAO.addProperty(property);
    }*/

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
