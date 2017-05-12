package com.services.impl;

import com.dao.DealDAO;
import com.dao.DealRequestDAO;
import com.dao.impl.DealDAOImpl;
import com.dao.impl.DealRequestDAOImpl;
import com.model.*;
import com.services.DealRequestService;
import com.services.DealService;
import com.services.DocumentService;
import com.services.TransactionService;
import com.services.shared.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class DealServiceImpl extends AbstractCrudService<Deal> implements DealService {

    public DealServiceImpl(ServiceSharedResources sharedResources) {
        super(ServiceId.DealService, sharedResources, Deal.class);
    }

    @Override
    public boolean signDeal(Deal deal, User broker) {
        deal.setBroker(broker);
        deal.setValidated(true);
        boolean updateSuccess = update(deal);

        if (updateSuccess) {
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

            return true;
        }

        return false;
    }

    @Override
    public List<Deal> listValidatedDeals() {
        return null;
    }

    @Override
    public List<Deal> listUnsignedDeals() {
        DealDAO dealDAO = new DealDAOImpl();
        return dealDAO.listUnsigned();
    }

    @Override
    public List<Deal> listBrokerDeals(User broker) {
        DealDAO dealDAO = new DealDAOImpl();
        return dealDAO.listBrokerDeals(broker);
    }

    @Override
    public List<Deal> listRealtorDeals(User realtor) {
        DealDAO dealDAO = new DealDAOImpl();
        return dealDAO.listRealtorValidatedDeals(realtor);
    }

    @Override
    public boolean hasDealOnOffer(Offer offer){
        DealDAO dealDAO = new DealDAOImpl();
        List<Deal> deals = dealDAO.listOfferDeals(offer);
        if(deals.size()!=0) return true;
        return false;
    }
}
