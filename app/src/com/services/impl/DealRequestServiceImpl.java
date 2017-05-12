package com.services.impl;

import com.dao.DealRequestDAO;
import com.dao.impl.DealRequestDAOImpl;
import com.model.Deal;
import com.model.DealRequest;
import com.model.User;
import com.services.DealRequestService;
import com.services.shared.AbstractCrudService;
import com.services.shared.ServiceId;
import com.services.shared.ServiceManager;
import com.services.shared.ServiceSharedResources;

import java.util.List;

public class DealRequestServiceImpl extends AbstractCrudService<DealRequest> implements DealRequestService {

    public DealRequestServiceImpl(ServiceSharedResources sharedResources) {
        super(ServiceId.DealRequestService, sharedResources, DealRequest.class);
    }

    @Override
    public boolean update(DealRequest request) {
        if (isConfirmed(request)) {
            // Create real deal based on the request
            Deal deal = new Deal();
            deal.setOffer(request.getOffer());
            deal.setBuyer(request.getBuyer());
            deal.setRealtor(request.getRealtor());

            // Remove request from database
            boolean deleteSucceeded = super.delete(request);
            if (!deleteSucceeded) {
                return false;
            }

            return ServiceManager.getInstance().getDealService().add(deal);
        } else {
            return super.update(request);
        }
    }

    @Override
    public boolean add(DealRequest request) {
        if (validate(request)) {
            // Add validation flag to request initiator
            User loggedUser = ServiceManager.getInstance().getAuthService().getLoggedUser();
            if (loggedUser != null && loggedUser.equals(request.getBuyer())) {
                request.setBuyerValidation(true);
            } else if (loggedUser != null && loggedUser.equals(request.getRealtor())) {
                request.setRealtorValidation(true);
            }

            DealRequestDAO dao = new DealRequestDAOImpl();
            if (dao.get(request.getId()) == null) {
                return dao.add(request);
            }
        }

        return super.add(request);
    }

    @Override
    public List<DealRequest> listUncommittedRealtorRequests(User realtor) {
        DealRequestDAO dao = new DealRequestDAOImpl();
        return dao.listUncommittedRealtorRequests(realtor);
    }

    @Override
    public List<DealRequest> listUncommittedBuyerRequests() {
        DealRequestDAO dao = new DealRequestDAOImpl();
        return dao.listUncommittedBuyerRequests();
    }

    @Override
    public List<DealRequest> listUncommittedSellerRequests(User seller) {
        DealRequestDAO dao = new DealRequestDAOImpl();
        return dao.listUncommittedSellerRequests(seller);
    }

    @Override
    public boolean isAlreadyRegistered(DealRequest request) {
        DealRequestDAO dao = new DealRequestDAOImpl();
        return dao.checkDealRequestClone(request);
    }

    private boolean validate(DealRequest request) {
        return request.getOffer() != null && request.getBuyer() != null;
    }

    private boolean isConfirmed(DealRequest request) {
        return request.getSellerValidation() && request.getBuyerValidation() && (request.getRealtor() == null || request.getRealtorValidation());
    }
}
