package com.services;

import com.model.*;
import com.services.shared.PermissionId;

public interface PermissionService {
    Permission getPermissionById(PermissionId permissionId);
    boolean hasPermission(RoleId role, Permission permission);
    boolean canEditOffer(User user, Offer offer);
    boolean canDeleteOffer(User user, Offer offer);
    boolean canAddOffers(User user);
    boolean canDeleteProperty(User user, Property property);
}
