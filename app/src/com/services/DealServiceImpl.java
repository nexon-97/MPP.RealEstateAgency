package com.services;

import com.dao.DealDAO;
import com.dao.DealDAOImpl;
import com.model.Deal;
import com.model.User;
import com.services.shared.BaseService;
import com.services.shared.ServiceId;
import com.services.shared.ServiceSharedResources;

import java.util.List;

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

    private boolean isDealValidated(Deal deal) {
        return  ((deal.getBroker() != null) &&
                (deal.getRealtor() != null) &&
                (deal.getBuyer() != null) &&
                (deal.getOffer() != null));
    }
}
