package com.dao;

import com.model.Property;
import com.model.User;
import com.utils.request.FilterParameter;
import com.utils.request.PropertyFilterParam;

import java.util.List;
import java.util.Map;

public interface PropertyDAO {
    Property getPropertyById(int id);
    boolean updateProperty(Property property);
    boolean deleteProperty(Property property);
    List<Property> getPropertiesOwnedByUser(User user);
    List<Property> list();
    List<Property> filter(Map<PropertyFilterParam, FilterParameter> filterParams);
}
