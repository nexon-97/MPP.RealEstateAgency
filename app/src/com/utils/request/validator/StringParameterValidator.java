package com.utils.request.validator;


import com.services.shared.ServiceManager;

import javax.servlet.http.HttpServletRequest;

public abstract class StringParameterValidator implements RequestParameterValidator<String>, RequestValueContainer<String>  {
    protected String paramName;
    protected String value;
    protected String errorMessage;
    protected boolean isNullAllowed;

    public StringParameterValidator(String paramName, boolean isNullAllowed){
        this.paramName = paramName;
        this.isNullAllowed = isNullAllowed;
    }

    @Override
    abstract public boolean validate();

    @Override
    public String getErrorMessage() {
        return this.errorMessage;
    }

    @Override
    public String getParameterName() {
        return this.paramName;
    }

    @Override
    public String getValue() {
        return this.value;
    }
}
