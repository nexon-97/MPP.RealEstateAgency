package com.services.shared;

public class BaseService {
    private ServiceSharedResources sharedResources;
    private ServiceId serviceId;

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
}
