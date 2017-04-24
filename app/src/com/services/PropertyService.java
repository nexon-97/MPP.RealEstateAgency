package com.services;

import com.model.Property;
import com.utils.request.FilterParameter;
import com.utils.request.PropertyFilterParam;

import java.util.List;
import java.util.Map;

public interface PropertyService {
    Property getPropertyById(int id);
    List<Property> filterProperties(Map<PropertyFilterParam, FilterParameter> filterParameters);
}
