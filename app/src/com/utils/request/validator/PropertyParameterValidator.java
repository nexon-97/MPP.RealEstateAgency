package com.utils.request.validator;

import com.model.Property;
import com.services.shared.ServiceManager;
import com.utils.request.ParseUtils;

import javax.servlet.http.HttpServletRequest;

public class PropertyParameterValidator implements RequestParameterValidator<Property>, RequestValueContainer<Property> {

    private String paramName;
    private Property value;
    protected String errorMessage;
    private boolean isNullAllowed;

    public PropertyParameterValidator(String paramName, boolean isNullAllowed) {
        this.paramName = paramName;
        this.isNullAllowed = isNullAllowed;
    }

    @Override
    public boolean validate() {
        this.errorMessage = null;
        this.value = null;

        HttpServletRequest request = ServiceManager.getInstance().getSharedResources().getRequest();

        String paramValue = request.getParameter(this.paramName);
        if (paramValue == null && this.isNullAllowed) {
            return true;
        }

        Integer id = ParseUtils.parseInteger(paramValue);
        if (id != null) {
            this.value = ServiceManager.getInstance().getPropertyService().get(id);
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
