package com.validators;

import com.model.Property;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class PropertyValidator implements Validator {

    private static final String EmptyString = "";

    public boolean supports(Class clazz) {
        return Property.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Property property = (Property) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "city", EmptyString);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "street", EmptyString);

        Integer distanceToSubway = property.getDistanceToSubway();
        Integer distanceToTransportStop = property.getDistanceToTransportStop();

        if (property.getType() == null) reject(errors, "type");
        if (property.getHouseNumber() == null || property.getHouseNumber() <= 0) reject(errors, "houseNumber");
        if (property.getArea() == null || property.getArea() <= 0) reject(errors, "area");
        if (property.getBlockNumber() != null && property.getBlockNumber() <= 0) reject(errors, "blockNumber");
        if (property.getFlatNumber() != null && property.getFlatNumber() <= 0) reject(errors, "flatNumber");
        if (distanceToSubway != null && distanceToSubway <= 0) reject(errors, "distanceToSubway");
        if (distanceToTransportStop != null && distanceToTransportStop <= 0) reject(errors, "distanceToTransportStop");
    }

    private void reject(Errors errors, String fieldName) {
        errors.rejectValue(fieldName, EmptyString);
    }
}
