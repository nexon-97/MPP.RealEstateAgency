package com.services;

import com.model.Permission;
import com.model.Role;
import com.services.shared.BaseService;
import com.services.shared.PermissionId;
import com.services.shared.ServiceId;
import com.services.shared.ServiceSharedResources;

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

    private void initIdMapping() {
        permissionIdMapping = new HashMap<>();

        permissionIdMapping.put(PermissionId.DisplayAdminbar, 1);
        permissionIdMapping.put(PermissionId.AddProperty, 2);
        permissionIdMapping.put(PermissionId.EditProperty, 3);
        permissionIdMapping.put(PermissionId.ViewProperty, 4);
        permissionIdMapping.put(PermissionId.RemoveProperty, 5);
    }
}
