package com.utils.request.validator;

import java.util.HashMap;
import java.util.Map;

public class RequestValidationChain {

    private Map<String, RequestParameterValidator> chain;
    private Map<String, String> errorMessageMap;
    private Map<String, Object> validatedValues;

    public RequestValidationChain() {
        this.chain = new HashMap<>();
    }

    public RequestValidationChain addValidator(RequestParameterValidator validator) {
        this.chain.put(validator.getParameterName(), validator);
        return this;
    }

    public Object getValue(String name) {
        RequestParameterValidator validator = this.chain.getOrDefault(name, null);
        if (validator != null) {
            return validator.getValue();
        }
        return null;
    }

    public boolean validate() {
        this.errorMessageMap = new HashMap<>();
        this.validatedValues = new HashMap<>();
        boolean isValidated = true;
        for (RequestParameterValidator validator : chain.values()) {
            if (!validator.validate()) {
                this.errorMessageMap.put(validator.getParameterName(), validator.getErrorMessage());
                isValidated = false;
            }
            else this.validatedValues.put(validator.getParameterName(), validator.getValue());
        }
        return isValidated;
    }

    public Map<String, String> getErrorMessageMap() {
        return this.errorMessageMap;
    }

    public Map<String, Object> getValidatedValues() { return this.validatedValues; }
}
