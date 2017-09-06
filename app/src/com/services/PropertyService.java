package com.services;

import com.model.Property;
import com.model.User;
import java.util.List;

public interface PropertyService extends CrudService<Property> {
    List<Property> getPropertiesOwnedByUser(User user);
}
