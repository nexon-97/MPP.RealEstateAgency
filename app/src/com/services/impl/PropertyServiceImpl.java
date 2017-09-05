package com.services.impl;

import com.dao.PropertyDAO;
import com.dao.impl.PropertyDAOImpl;
import com.model.Property;
import com.model.User;
import com.model.PropertyType;

import com.services.PropertyService;
import com.services.shared.BaseService;
import com.utils.request.validator.RequestValidationChain;

import java.util.List;
import java.util.Map;

public class PropertyServiceImpl extends BaseService implements PropertyService {

    @Override
    public Property getPropertyById(int id) {
        PropertyDAO dao = new PropertyDAOImpl();

        return dao.getPropertyById(id);
    }

    @Override
    public boolean addProperty(RequestValidationChain requestValidationChain) {
        Property property = new Property();
        PropertyDAO propertyDAO = new PropertyDAOImpl();
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

        /*User loggedUser = ServiceManager.getInstance().getAuthService().getLoggedUser();
        property.setOwner(loggedUser);

        Map<String, Object> model = getSharedResources().getModel();
        return propertyDAO.addProperty(property);*/
        return false;
    }

    @Override
    public boolean updateProperty(Map<String, String[]> params) {
        return false;
    }

    @Override
    public boolean deleteProperty(Property property) {
        /*User loggedUser = ServiceManager.getInstance().getAuthService().getLoggedUser();
        boolean hasPermission = ServiceManager.getInstance().getPermissionService().canDeleteProperty(loggedUser, property);

        if (hasPermission) {
            PropertyDAO propertyDAO = new PropertyDAOImpl();
            return propertyDAO.deleteProperty(property);
        }*/

        return false;
    }

    @Override
    public List<Property> getPropertiesOwnedByUser(User user) {
        PropertyDAO propertyDAO = new PropertyDAOImpl();

        return propertyDAO.getPropertiesOwnedByUser(user);
    }

    @Override
    public List<Property> getList() {
        PropertyDAO propertyDAO = new PropertyDAOImpl();
        return propertyDAO.list();
    }

    private boolean checkNotNullNumber(Integer data) {
        return data>0;
    }
}
