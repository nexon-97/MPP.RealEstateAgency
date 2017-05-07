package com.services.shared;

import com.utils.request.ErrorRegistry;

public class BaseService implements ErrorRegistry {

    private ServiceSharedResources sharedResources;
    private ServiceId serviceId;
    private String errorMessage;
    private int errorCode;

    public BaseService(ServiceId serviceId, ServiceSharedResources sharedResources) {
        this.serviceId = serviceId;
        this.sharedResources = sharedResources;
    }

    public ServiceSharedResources getSharedResources() {
        return sharedResources;
    }

    public ServiceId getId() {
        return serviceId;
    }

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
