package com.services;

import com.model.*;
import com.services.shared.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PermissionServiceImpl extends BaseService implements PermissionService {

    public PermissionServiceImpl(ServiceSharedResources sharedResources) {
        super(ServiceId.PermissionService, sharedResources);
    }

    @Override
    public boolean canEditOffer(User user, Offer offer) {
        if (offer != null && user != null) {
            boolean isOwner = (user.getId() == offer.getProperty().getOwner().getId());
            return isOwner;
        }

        return false;
    }

    @Override
    public boolean canDeleteOffer(User user, Offer offer) {
        if (offer != null && user != null) {
            boolean isOwner = (user.getId() == offer.getProperty().getOwner().getId());
            return isOwner || isLoggedUserAdmin();
        }

        return false;
    }

    @Override
    public boolean canAddOffers(User user) {
        return user.getRoleId().equals(RoleId.User);
    }

    public boolean canDeleteProperty(User user, Property property) {
        if (property != null && user != null) {
            boolean isOwner = (user.getId() == property.getOwner().getId());
            return isOwner || isLoggedUserAdmin();
        }

        return false;
    }

    private boolean isLoggedUserAdmin() {
        User loggedUser = ServiceManager.getInstance().getAuthService().getLoggedUser();
        return (loggedUser != null && loggedUser.getRoleId().equals(RoleId.Admin));
    }
}
