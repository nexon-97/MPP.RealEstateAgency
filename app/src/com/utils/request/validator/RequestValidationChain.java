package com.utils.request.validator;

import java.util.HashMap;
import java.util.Map;

public class RequestValidationChain {

    private Map<String, RequestParameterValidator> chain;
    private String errorMessage;

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
        for (RequestParameterValidator validator : chain.values()) {
            if (!validator.validate()) {
                this.errorMessage = validator.getErrorMessage();
                return false;
            }
        }

        return true;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }
}
