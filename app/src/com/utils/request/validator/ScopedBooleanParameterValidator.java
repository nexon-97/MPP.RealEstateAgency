package com.utils.request.validator;

import javax.servlet.http.HttpServletRequest;

public class ScopedBooleanParameterValidator implements RequestParameterValidator<Boolean>, RequestValueContainer<Boolean> {

    private String scope;
    private String paramName;
    private boolean value;
    private String errorMessage;

    public ScopedBooleanParameterValidator(String scope, String paramName) {
        this.scope = scope;
        this.paramName = paramName;
    }

    @Override
    public boolean validate(HttpServletRequest request) {
        this.errorMessage = null;
        String[] paramValues = request.getParameterValues(this.scope);

        this.value = false;
        if (paramValues != null) {
            for (String value : paramValues) {
                if (value.equals(this.paramName)) {
                    this.value = true;
                    break;
                }
            }
        }

        return true;
    }

    @Override
    public Boolean getValue() {
        return this.value;
    }

    @Override
    public String getErrorMessage() {
        return this.errorMessage;
    }

    @Override
    public String getParameterName() {
        return this.paramName;
    }
}
