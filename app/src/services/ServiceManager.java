package services;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

public class ServiceManager {
    private static ServiceManager instance = null;

    private HashMap<String, Service> servicesMap;
    private HttpServletRequest request;

    protected ServiceManager(HttpServletRequest request) {
        this.request = request;
        servicesMap = new HashMap<>();
        servicesMap.put("AuthService", new AuthServiceImpl(request));
    }

    public Service getService(String name) {
        return (servicesMap.containsKey(name)) ? servicesMap.get(name) : null;
    }

    public static ServiceManager getInstance(HttpServletRequest request) {
        if (instance == null) {
            instance = new ServiceManager(request);
        }

        return instance;
    }
}
