package com.dao;

import com.model.Property;
import com.model.User;

import java.util.List;

public interface PropertyDAO {
    Property getPropertyById(int id);
    boolean updateProperty(Property property);
    boolean deleteProperty(Property property);
    List<Property> getPropertiesOwnedByUser(User user);
    List<Property> list();
}
