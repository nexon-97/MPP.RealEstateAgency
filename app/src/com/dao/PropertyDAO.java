package com.dao;

import com.model.Property;
import com.model.User;
import java.util.List;

public interface PropertyDAO extends CrudDAO<Property> {
    List<Property> getPropertiesOwnedByUser(User user);
}
