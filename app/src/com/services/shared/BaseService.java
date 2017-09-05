package com.services.shared;

import com.utils.request.ErrorRegistry;

public class BaseService implements ErrorRegistry {

    private String errorMessage;
    private int errorCode;


    @Override
    public int getErrorCode() {
        return this.errorCode;
    }

    @Override
    public String getErrorMessage() {
        return this.errorMessage;
    }

    public void setErrorInfo(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
