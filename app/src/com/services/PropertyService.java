package com.services;

import com.model.Property;
import com.model.User;
import com.utils.request.validator.RequestValidationChain;

import java.util.List;

public interface PropertyService extends CrudService<Property> {
    boolean add(RequestValidationChain requestValidationChain);
    List<Property> listUserOwnedProperties(User owner);
}
