package com.services;

import com.model.Deal;
import com.model.User;

import java.util.List;

public interface DealService {
    Deal getDealById(int id);
    boolean addDeal(Deal deal);
    boolean updateDeal(Deal deal);
    boolean deleteDeal(Deal deal);
    boolean assignDealBroker(Deal deal, User user);
    boolean assignDealRealtor(Deal deal, User user);
    List<Deal> list();
    List<Deal> listValidatedBrokerDeals(User user);
    List<Deal> listUpcomingBrokerDeals(User user);
    List<Deal> listValidatedRealtorDeals(User user);
}
