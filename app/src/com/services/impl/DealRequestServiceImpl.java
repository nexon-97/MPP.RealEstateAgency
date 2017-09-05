package com.services.impl;

import com.dao.DealRequestDAO;
import com.dao.impl.DealRequestDAOImpl;
import com.model.Deal;
import com.model.DealRequest;
import com.model.User;
import com.services.AuthService;
import com.services.DealRequestService;
import com.services.DealService;
import com.services.shared.AbstractCrudService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class DealRequestServiceImpl extends AbstractCrudService<DealRequest> implements DealRequestService {

    @Autowired
    DealRequestDAO dealRequestDAO;

    @Autowired
    DealService dealService;

    @Autowired
    AuthService authService;

    public DealRequestServiceImpl() {
        super(DealRequest.class);
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

            return dealService.add(deal);
        } else {
            return super.update(request);
        }
    }

    @Override
    public boolean add(DealRequest request) {
        if (validate(request)) {
            // Add validation flag to request initiator
            User loggedUser = authService.getLoggedUser();
            if (loggedUser != null && loggedUser.equals(request.getBuyer())) {
                request.setBuyerValidation(true);
            } else if (loggedUser != null && loggedUser.equals(request.getRealtor())) {
                request.setRealtorValidation(true);
            }

            if (dealRequestDAO.get(request.getId()) == null) {
                return dealRequestDAO.add(request);
            }
        }

        return super.add(request);
    }

    @Override
    public List<DealRequest> listUncommittedRealtorRequests(User realtor) {
        return dealRequestDAO.listUncommittedRealtorRequests(realtor);
    }

    @Override
    public List<DealRequest> listUncommittedBuyerRequests() {
        return dealRequestDAO.listUncommittedBuyerRequests();
    }

    @Override
    public List<DealRequest> listUncommittedSellerRequests(User seller) {
        return dealRequestDAO.listUncommittedSellerRequests(seller);
    }

    @Override
    public boolean isAlreadyRegistered(DealRequest request) {
        return dealRequestDAO.checkDealRequestClone(request);
    }

    private boolean validate(DealRequest request) {
        return request.getOffer() != null && request.getBuyer() != null;
    }

    private boolean isConfirmed(DealRequest request) {
        return request.getSellerValidation() && request.getBuyerValidation() && (request.getRealtor() == null || request.getRealtorValidation());
    }
}
