package com.services.shared;

import com.model.*;
import com.services.*;
import com.services.impl.*;
import org.springframework.context.ApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class ServiceManager {
    private static ServiceManager instance = null;

    private ServiceSharedResources sharedResources;
    private Map<ServiceId, BaseService> servicesMap;
    private Map<Class<? extends Entity>, CrudService> crudServicesMap;

    protected ServiceManager(ApplicationContext context, HttpServletRequest request, HttpServletResponse response) {
        sharedResources = new ServiceSharedResourcesImpl(context, request, response);
        servicesMap = new HashMap<>();
        crudServicesMap = new HashMap<>();
    }

    private void init() {
        servicesMap.put(ServiceId.PermissionService, new PermissionServiceImpl(sharedResources));
        servicesMap.put(ServiceId.AuthorizationService, new AuthServiceImpl(sharedResources));
        servicesMap.put(ServiceId.RegistrationService, new RegisterServiceImpl(sharedResources));
        servicesMap.put(ServiceId.PropertyService, new PropertyServiceImpl(sharedResources));
        servicesMap.put(ServiceId.UserService, new UserServiceImpl(sharedResources));
        servicesMap.put(ServiceId.OfferService, new OfferServiceImpl(sharedResources));
        servicesMap.put(ServiceId.TransactionService, new TransactionServiceImpl(sharedResources));
        servicesMap.put(ServiceId.DealService, new DealServiceImpl(sharedResources));
        servicesMap.put(ServiceId.DealRequestService, new DealRequestServiceImpl(sharedResources));
        servicesMap.put(ServiceId.DocumentService, new DocumentServiceImpl(sharedResources));

        crudServicesMap.put(Property.class, getPropertyService());
        crudServicesMap.put(User.class, getUserService());
        crudServicesMap.put(Offer.class, getOfferService());
        crudServicesMap.put(Transaction.class, getTransactionService());
        crudServicesMap.put(Deal.class, getDealService());
        crudServicesMap.put(DealRequest.class, getDealRequestService());
        crudServicesMap.put(Document.class, getDocumentService());
    }

    public BaseService getServiceById(ServiceId id) {
        return servicesMap.getOrDefault(id, null);
    }

    public AuthService getAuthService() {
        return (AuthService) getServiceById(ServiceId.AuthorizationService);
    }

    public RegisterService getRegisterService() {
        return (RegisterService) getServiceById(ServiceId.RegistrationService);
    }

    public PermissionService getPermissionService() {
        return (PermissionService) getServiceById(ServiceId.PermissionService);
    }

    public PropertyService getPropertyService() {
        return (PropertyService) getServiceById(ServiceId.PropertyService);
    }

    public UserService getUserService() {
        return (UserService) getServiceById(ServiceId.UserService);
    }

    public OfferService getOfferService() {
        return (OfferService) getServiceById(ServiceId.OfferService);
    }
  
    public TransactionService getTransactionService() {
        return (TransactionService) getServiceById(ServiceId.TransactionService);
    }

    public DealService getDealService() {
        return (DealService) getServiceById(ServiceId.DealService);
    }

    public DealRequestService getDealRequestService() {
        return (DealRequestService) getServiceById(ServiceId.DealRequestService);
    }

    public DocumentService getDocumentService() {
        return (DocumentService) getServiceById(ServiceId.DocumentService);
    }

    public ServiceSharedResources getSharedResources() {
        return sharedResources;
    }

    public <E extends Entity> CrudService<E> getCrudService(Class<E> metaclass) {
        return crudServicesMap.getOrDefault(metaclass, null);
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
