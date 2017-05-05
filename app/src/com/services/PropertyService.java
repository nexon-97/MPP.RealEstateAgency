package com.services;

import com.model.Property;
import com.model.User;
import com.utils.request.validator.RequestValidationChain;

import java.util.List;
import java.util.Map;

public interface PropertyService {
    Property getPropertyById(int id);
    boolean addProperty(RequestValidationChain requestValidationChain);
    boolean updateProperty(Map<String, String[]> params);
    List<Property> getPropertiesOwnedByUser(User user);
    List<Property> getList();
}
