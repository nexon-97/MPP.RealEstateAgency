package com.services;

import org.springframework.context.ApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class ServiceManager {
    private static ServiceManager instance = null;

    private ServiceSharedResources sharedResources;
    private Map<ServiceId, BaseService> servicesMap;

    protected ServiceManager(ApplicationContext context, HttpServletRequest request, HttpServletResponse response) {
        sharedResources = new ServiceSharedResourcesImpl(context, request, response);
        servicesMap = new HashMap<>();
    }

    private void init() {
        servicesMap.put(ServiceId.PermissionService, new PermissionServiceImpl(sharedResources));
        servicesMap.put(ServiceId.AuthorizationService, new AuthServiceImpl(sharedResources));
        servicesMap.put(ServiceId.PropertyService, new PropertyServiceImpl(sharedResources));
    }

    public BaseService getServiceById(ServiceId id) {
        return (servicesMap.containsKey(id)) ? servicesMap.get(id) : null;
    }

    public AuthService getAuthService() {
        return (AuthService) getServiceById(ServiceId.AuthorizationService);
    }

    public PermissionService getPermissionService() {
        return (PermissionService) getServiceById(ServiceId.PermissionService);
    }

    public PropertyService getPropertyService() {
        return (PropertyService) getServiceById(ServiceId.PropertyService);
    }

    public ServiceSharedResources getSharedResources() {
        return sharedResources;
    }

    public static ServiceManager build(ApplicationContext context, HttpServletRequest request, HttpServletResponse response) {
        assert(instance == null);
        instance = new ServiceManager(context, request, response);
        instance.init();

        return instance;
    }

    public static ServiceManager getInstance() {
        return instance;
    }
}
