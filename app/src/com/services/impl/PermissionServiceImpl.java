package com.services.impl;

import com.model.*;
import com.services.AuthService;
import com.services.PermissionService;
import com.services.shared.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class PermissionServiceImpl extends BaseService implements PermissionService {

    @Autowired
    AuthService authService;

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

    public boolean canDeleteProperty(User user, Property property) {
        if (property != null && user != null) {
            boolean isOwner = (user.getId() == property.getOwner().getId());
            return isOwner || isLoggedUserAdmin();
        }

        return false;
    }

    @Override
    public boolean canEditUserInfo(User user) {
        User loggedUser = authService.getLoggedUser();
        return (loggedUser != null && user != null && (loggedUser.equals(user) || loggedUser.getRoleId() == RoleId.Admin));
    }

    private boolean isLoggedUserAdmin() {
        User loggedUser = authService.getLoggedUser();
        return (loggedUser != null && loggedUser.getRoleId().equals(RoleId.Admin));
    }
}
