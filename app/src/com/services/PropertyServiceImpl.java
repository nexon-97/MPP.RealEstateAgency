package com.services;

import com.dao.PropertyDAO;
import com.dao.PropertyDAOImpl;
import com.model.Property;

public class PropertyServiceImpl extends BaseService implements PropertyService {
    public PropertyServiceImpl(ServiceSharedResources sharedResources) {
        super(ServiceId.PropertyService, sharedResources);
    }

    @Override
    public Property getPropertyById(int id) {
        PropertyDAO dao = new PropertyDAOImpl();

        return dao.getPropertyById(id);
    }
}
