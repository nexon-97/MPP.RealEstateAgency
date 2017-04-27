package com.services;

import com.model.Property;
import com.utils.request.FilterParameter;
import com.utils.request.PropertyFilterParamId;

import java.util.List;
import java.util.Map;

public interface PropertyService {
    Property getPropertyById(int id);
    boolean addProperty(Map<String, String[]> params);
    boolean updateProperty(Map<String, String[]> params);
    List<Property> filterProperties(Map<PropertyFilterParamId, FilterParameter> filterParameters);
}
