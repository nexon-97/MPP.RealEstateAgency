package com.services;

import com.dao.DealDAO;
import com.dao.DealDAOImpl;
import com.model.*;
import com.services.shared.BaseService;
import com.services.shared.ServiceId;
import com.services.shared.ServiceManager;
import com.services.shared.ServiceSharedResources;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class DealServiceImpl extends BaseService implements DealService {

    public DealServiceImpl(ServiceSharedResources sharedResources) {
        super(ServiceId.DealService, sharedResources);
    }

    @Override
    public Deal getDealById(int id) {
        DealDAO dao = new DealDAOImpl();

        return dao.getDealById(id);
    }

    @Override
    public boolean addDeal(Deal deal) {
        DealDAO dao = new DealDAOImpl();

        return dao.addDeal(deal);
    }

    @Override
    public boolean updateDeal(Deal deal) {
        DealDAO dao = new DealDAOImpl();

        return dao.update(deal);
    }

    @Override
    public boolean deleteDeal(Deal deal) {
        return false;
    }

    @Override
    public boolean assignDealBroker(Deal deal, User user) {
        deal.setBroker(user);
        deal.setValidated(isDealValidated(deal));

        if(deal.getValidated()){
            TransactionService transactionService = new TransactionServiceImpl(getSharedResources());
            transactionService.addTransaction(deal.getBuyer(), deal.getOffer().getProperty().getOwner(), new BigDecimal(deal.getOffer().getCost().doubleValue() * 0.02), deal.getOffer().getCost());
            DocumentService documentService = new DocumentServiceImpl();
            Document document = new Document();
            document.setSeller(deal.getOffer().getProperty().getOwner());
            document.setBuyer(deal.getBuyer());
            document.setOffer(deal.getOffer());
            document.setConfirmDate(new Date());

            if (deal.getOffer().getOfferType().equals(OfferType.Sale)){
                document.setDocumentType(DocumentType.DocumentOfSell.ordinal());
                document.setGraduationDate(null);
            } else {
                document.setDocumentType(DocumentType.DocumentOfRent.ordinal());
                document.setGraduationDate(new Date());// TODO
            }
            documentService.addDocument(document);
        }

        return updateDeal(deal);
    }

    @Override
    public boolean assignDealRealtor(Deal deal, User user) {
        deal.setRealtor(user);
        deal.setValidated(isDealValidated(deal));
        return updateDeal(deal);
    }

    @Override
    public List<Deal> list() {
        DealDAO dao = new DealDAOImpl();

        return dao.list();
    }

    @Override
    public List<Deal> listValidatedBrokerDeals(User user) {
        DealDAO dao = new DealDAOImpl();

        return dao.listBrokerValidatedDeals(user);
    }

    @Override
    public List<Deal> listUpcomingBrokerDeals(User user) {
        DealDAO dao = new DealDAOImpl();

        return dao.listBrokerNonValidated(user);
    }

    @Override
    public List<Deal> listValidatedRealtorDeals(User user) {
        DealDAO dao = new DealDAOImpl();

        return dao.listRealtorValidatedDeals(user);
    }

    @Override
    public boolean addDealRequest(DealRequest request) {
        boolean requestValid = validateDealRequest(request);
        if (requestValid) {
            // Add validation flag to request initiator
            User loggedUser = ServiceManager.getInstance().getAuthService().getLoggedUser();
            if (Objects.equals(request.getBuyer(), loggedUser)) {
                request.setBuyerValidation(true);
            } else if (Objects.equals(request.getRealtor(), loggedUser)) {
                request.setRealtorValidation(true);
            }

            DealDAO dao = new DealDAOImpl();

            if (!dao.isDealRequestAlreadyAdded(request)) {
                return dao.addDealRequest(request);
            }
        }

        return false;
    }

    @Override
    public boolean isDealRequestRegistered(DealRequest request) {
        DealDAO dao = new DealDAOImpl();
        return dao.isDealRequestAlreadyAdded(request);
    }

    @Override
    public List<DealRequest> listUncommittedRealtorRequests(User realtor) {
        DealDAO dao = new DealDAOImpl();
        return dao.listUncommittedRealtorRequests(realtor);
    }

    @Override
    public List<DealRequest> listUncommittedBuyerRequests(User realtor) {
        return null;
    }

    @Override
    public List<DealRequest> listUncommittedSellerRequests(User realtor) {
        DealDAO dao = new DealDAOImpl();
        return dao.listUncommittedSellerRequests(realtor);
    }

    @Override
    public DealRequest getDealRequestById(int id) {
        DealDAO dao = new DealDAOImpl();
        return dao.getDealRequestById(id);
    }

    @Override
    public boolean updateDealRequest(DealRequest request) {
        DealDAO dao = new DealDAOImpl();

        if (isDealRequestConfirmed(request)) {
            // Create real deal based on the request
            Deal deal = new Deal();
            deal.setOffer(request.getOffer());
            deal.setBuyer(request.getBuyer());
            deal.setRealtor(request.getRealtor());

            // Remove request from database
            dao.deleteDealRequest(request);

            // Add real deal
            return dao.addDeal(deal);
        } else {
            return dao.updateDealRequest(request);
        }
    }

    private boolean isDealValidated(Deal deal) {
        return  ((deal.getBroker() != null) &&
                (deal.getRealtor() != null) &&
                (deal.getBuyer() != null) &&
                (deal.getOffer() != null));
    }

    private boolean validateDealRequest(DealRequest request) {
        return request.getOffer() != null && request.getBuyer() != null;
    }

    private boolean isDealRequestConfirmed(DealRequest request) {
        return request.getSellerValidation() && request.getBuyerValidation() && (request.getRealtor() == null || request.getRealtorValidation());
    }
}
