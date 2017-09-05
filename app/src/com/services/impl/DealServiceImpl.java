package com.services.impl;

import com.dao.DealDAO;
import com.model.*;
import com.services.DealService;
import com.services.DocumentService;
import com.services.TransactionService;
import com.services.shared.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class DealServiceImpl extends AbstractCrudService<Deal> implements DealService {

    @Autowired
    private TransactionService transactionService;

    private DealDAO dealDAO;

    @Autowired
    public DealServiceImpl(DealDAO dealDAO) {
        super(dealDAO);
        this.dealDAO = dealDAO;
    }

    @Override
    public boolean signDeal(Deal deal, User broker) {
        deal.setBroker(broker);
        deal.setValidated(true);
        boolean updateSuccess = update(deal);

        if (updateSuccess) {
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
        return dealDAO.listUnsigned();
    }

    @Override
    public List<Deal> listBrokerDeals(User broker) {
        return dealDAO.listBrokerDeals(broker);
    }

    @Override
    public List<Deal> listRealtorDeals(User realtor) {
        return dealDAO.listRealtorValidatedDeals(realtor);
    }

    @Override
    public boolean hasDealOnOffer(Offer offer){
        List<Deal> deals = dealDAO.listOfferDeals(offer);
        return (deals.size() != 0);
    }
}
