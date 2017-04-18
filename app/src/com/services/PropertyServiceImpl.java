package com.services;

import com.dao.PropertyDAO;
import com.dao.PropertyDAOImpl;
import com.model.Property;
import com.services.shared.BaseService;
import com.services.shared.ServiceId;
import com.services.shared.ServiceSharedResources;

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
