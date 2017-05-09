package com.services;

import com.model.*;
import com.services.shared.PermissionId;

public interface PermissionService {
    Permission getPermissionById(PermissionId permissionId);
    boolean hasPermission(RoleId role, Permission permission);
    boolean grantPermission(RoleId role, Permission permission);
    boolean removePermission(RoleId role, Permission permission);
    boolean canEditOffer(User user, Offer offer);
    boolean canDeleteOffer(User user, Offer offer);
    boolean canDeleteProperty(User user, Property property);
}
