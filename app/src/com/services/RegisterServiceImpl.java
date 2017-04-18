package com.services;

import com.services.shared.BaseService;
import com.services.shared.ServiceId;
import com.services.shared.ServiceSharedResources;

public class RegisterServiceImpl extends BaseService implements RegisterService {

    public RegisterServiceImpl(ServiceSharedResources sharedResources) {
        super(ServiceId.RegistrationService, sharedResources);
    }

    @Override
    public boolean register() {
        return false;
    }
}
