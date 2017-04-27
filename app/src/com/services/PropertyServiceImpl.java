package com.services;

import com.dao.PropertyDAO;
import com.dao.PropertyDAOImpl;
import com.model.Property;
import com.model.User;
import com.services.shared.BaseService;
import com.services.shared.ServiceId;
import com.services.shared.ServiceSharedResources;
import com.utils.request.FilterParameter;
import com.utils.request.PropertyFilterParamId;

import java.util.List;
import java.util.Map;

public class PropertyServiceImpl extends BaseService implements PropertyService {
    public PropertyServiceImpl(ServiceSharedResources sharedResources) {
        super(ServiceId.PropertyService, sharedResources);
    }

    @Override
    public Property getPropertyById(int id) {
        PropertyDAO dao = new PropertyDAOImpl();

        return dao.getPropertyById(id);
    }

    @Override
    public boolean addProperty(Property property) {
        return false;
    }

    @Override
    public boolean updateProperty(Property property) {
        return false;
    }

    @Override
    public List<Property> filterProperties(Map<PropertyFilterParamId, FilterParameter> filterParameters) {
        PropertyDAO dao = new PropertyDAOImpl();

        return dao.filter(filterParameters);
    }

    @Override
    public List<Property> getPropertiesOwnedByUser(User user) {
        PropertyDAO propertyDAO = new PropertyDAOImpl();

        return propertyDAO.getPropertiesOwnedByUser(user);
    }
}
