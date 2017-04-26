package com.services;

import com.model.Role;
import com.model.Permission;
import com.services.shared.PermissionId;

public interface PermissionService {
    Permission getPermissionById(PermissionId permissionId);
    boolean hasPermission(Role role, Permission permission);
    boolean grantPermission(Role role, Permission permission);
    boolean removePermission(Role role, Permission permission);
}
