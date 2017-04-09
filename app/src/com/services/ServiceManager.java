package com.services;

import java.util.HashMap;

public class ServiceManager {
    private static ServiceManager instance = null;

    private HashMap<String, Service> servicesMap;

    protected ServiceManager() {
        servicesMap = new HashMap<>();
        servicesMap.put("AuthService", new AuthServiceImpl());
    }

    public Service getService(String name) {
        return (servicesMap.containsKey(name)) ? servicesMap.get(name) : null;
    }

    public static ServiceManager getInstance() {
        if (instance == null) {
            instance = new ServiceManager();
        }

        return instance;
    }
}
