package com.services.shared;

import com.utils.request.ErrorRegistry;

public class BaseService implements ErrorRegistry {

    private String errorMessage;


    @Override
    public String getErrorMessage() {
        return this.errorMessage;
    }

    public void setErrorInfo(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
