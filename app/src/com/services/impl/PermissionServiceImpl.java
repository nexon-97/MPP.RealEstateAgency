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
            return isOwner || user.getRoleId().equals(RoleId.Admin);
        }

        return false;
    }

    public boolean canDeleteProperty(User user, Property property) {
        if (property != null && user != null) {
            boolean isOwner = (user.getId() == property.getOwner().getId());
            return isOwner || user.getRoleId().equals(RoleId.Admin);
        }

        return false;
    }

    @Override
    public boolean canEditUserInfo(User editorUser, User user) {
        return (editorUser != null && user != null && (editorUser.equals(user) || editorUser.getRoleId() == RoleId.Admin));
    }
}
