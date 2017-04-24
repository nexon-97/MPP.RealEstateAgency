package com.services;

import com.dao.PropertyDAO;
import com.dao.PropertyDAOImpl;
import com.model.Property;
import com.services.shared.BaseService;
import com.services.shared.ServiceId;
import com.services.shared.ServiceSharedResources;
import com.utils.request.FilterParameter;
import com.utils.request.PropertyFilterParam;

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
    public List<Property> filterProperties(Map<PropertyFilterParam, FilterParameter> filterParameters) {
        PropertyDAO dao = new PropertyDAOImpl();

        return dao.filter(filterParameters);
    }
}
