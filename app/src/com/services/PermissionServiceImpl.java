package com.services;

import com.model.*;
import com.services.shared.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PermissionServiceImpl extends BaseService implements PermissionService {

    private Map<PermissionId, Integer> permissionIdMapping;

    public PermissionServiceImpl(ServiceSharedResources sharedResources) {
        super(ServiceId.PermissionService, sharedResources);

        initIdMapping();
    }

    @Override
    public Permission getPermissionById(PermissionId permissionId) {
        Permission p = new Permission();
        p.setId(permissionIdMapping.get(permissionId));

        return p;
    }

    @Override
    public boolean hasPermission(Role role, Permission permission) {
        Set<Permission> permissions = role.getPermissions();
        for (Permission p : permissions) {
            if (p.equals(permission)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean grantPermission(Role role, Permission permission) {
        return false;
    }

    @Override
    public boolean removePermission(Role role, Permission permission) {
        return false;
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

    private boolean isLoggedUserAdmin() {
        User loggedUser = ServiceManager.getInstance().getAuthService().getLoggedUser();
        return (loggedUser != null && loggedUser.getRole().getRoleId().equals(RoleId.Admin));
    }

    private void initIdMapping() {
        permissionIdMapping = new HashMap<>();

        permissionIdMapping.put(PermissionId.DisplayAdminbar, 1);
        permissionIdMapping.put(PermissionId.AddProperty, 2);
        permissionIdMapping.put(PermissionId.EditProperty, 3);
        permissionIdMapping.put(PermissionId.ViewProperty, 4);
        permissionIdMapping.put(PermissionId.RemoveProperty, 5);
    }
}
