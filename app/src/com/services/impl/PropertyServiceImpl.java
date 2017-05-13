package com.services.impl;

import com.dao.PropertyDAO;
import com.dao.impl.PropertyDAOImpl;
import com.model.Property;
import com.model.User;
import com.model.PropertyType;

import com.services.PropertyService;
import com.services.shared.*;
import com.utils.request.validator.RequestValidationChain;

import java.util.List;

public class PropertyServiceImpl extends AbstractCrudService<Property> implements PropertyService {

    public PropertyServiceImpl(ServiceSharedResources sharedResources) {
        super(ServiceId.PropertyService, sharedResources, Property.class);
    }

    @Override
    public boolean add(RequestValidationChain requestValidationChain) {
        Property property = new Property();
        boolean isCorrectFields = true;

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

        User loggedUser = ServiceManager.getInstance().getAuthService().getLoggedUser();
        property.setOwner(loggedUser);

        super.add(property);
        return true;
    }

    @Override
    public List<Property> listUserOwnedProperties(User user) {
        PropertyDAO propertyDAO = new PropertyDAOImpl();

        return propertyDAO.getPropertiesOwnedByUser(user);
    }

    private boolean checkNotNullNumber(Integer data) {
        return data > 0;
    }
}
