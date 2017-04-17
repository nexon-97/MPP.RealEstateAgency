package com.services;

public class RegisterServiceImpl extends BaseService implements RegisterService {

    public RegisterServiceImpl(ServiceSharedResources sharedResources) {
        super(ServiceId.RegistrationService, sharedResources);
    }

    @Override
    public boolean register() {
        return false;
    }
}
