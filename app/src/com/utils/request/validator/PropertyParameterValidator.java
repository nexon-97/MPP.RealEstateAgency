package com.utils.request.validator;

import com.model.Property;
import com.services.PropertyService;
import com.utils.request.ParseUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

public class PropertyParameterValidator implements RequestParameterValidator<Property>, RequestValueContainer<Property> {

    private String paramName;
    private Property value;
    protected String errorMessage;
    private boolean isNullAllowed;
    PropertyService propertyService;

    public PropertyParameterValidator(PropertyService propertyService, String paramName, boolean isNullAllowed) {
        this.paramName = paramName;
        this.isNullAllowed = isNullAllowed;
        this.propertyService = propertyService;
    }

    @Override
    public boolean validate(HttpServletRequest request) {
        this.errorMessage = null;
        this.value = null;

        String paramValue = request.getParameter(this.paramName);
        if (paramValue == null && this.isNullAllowed) {
            return true;
        }

        Integer id = ParseUtils.parseInteger(paramValue);
        if (id != null) {
            this.value = propertyService.getPropertyById(id);
        }

        if (this.value == null) {
            this.errorMessage = "Невозможно получить информацию о собственности!";
        }

        return this.value != null;
    }

    @Override
    public String getErrorMessage() {
        return this.errorMessage;
    }

    @Override
    public String getParameterName() {
        return this.paramName;
    }

    @Override
    public Property getValue() {
        return this.value;
    }
}
