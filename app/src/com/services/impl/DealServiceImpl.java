package com.services.impl;

import com.dao.DealDAO;
import com.dao.impl.DealDAOImpl;
import com.model.*;
import com.model.Transaction;
import com.services.DealService;
import com.services.DocumentService;
import com.services.TransactionService;
import com.services.shared.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class DealServiceImpl extends AbstractCrudService<Deal> implements DealService {

    public DealServiceImpl(ServiceSharedResources sharedResources) {
        super(ServiceId.DealService, sharedResources, Deal.class);
    }

    @Override
    public boolean signDeal(Deal deal, User broker) {
        deal.setBroker(broker);
        deal.setValidated(true);

            update(deal);

            Transaction transaction = new Transaction();
            transaction.setBuyer(deal.getBuyer());
            transaction.setSeller(deal.getOffer().getProperty().getOwner());
            transaction.setCompanyFine(new BigDecimal(deal.getOffer().getCost().doubleValue() * 0.02));
            transaction.setPayment(deal.getOffer().getCost());

            TransactionService transactionService = ServiceManager.getInstance().getTransactionService();
            transactionService.add(transaction);

            Document document = new Document();
            document.setSeller(deal.getOffer().getProperty().getOwner());
            document.setBuyer(deal.getBuyer());
            document.setOffer(deal.getOffer());
            document.setConfirmDate(new Date());

            DocumentService documentService = ServiceManager.getInstance().getDocumentService();

            if (deal.getOffer().getOfferType().equals(OfferType.Sale)){
                document.setDocumentType(DocumentType.DocumentOfSell.ordinal());
                document.setGraduationDate(null);
            } else {
                document.setDocumentType(DocumentType.DocumentOfRent.ordinal());
                document.setGraduationDate(new Date());// TODO
            }
            documentService.add(document);

            return true;
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
