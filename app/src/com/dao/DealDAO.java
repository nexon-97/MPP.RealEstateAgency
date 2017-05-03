package com.dao;

import com.model.Deal;
import com.model.User;

import java.util.List;

public interface DealDAO {
    boolean addDeal(Deal deal);
    Deal getDealById(int id);
    boolean update(Deal deal);
    List<Deal> listValidated();
    List<Deal> listNonValidated();
    List<Deal> list();
    List<Deal> listBrokerValidatedDeals(User user);
    List<Deal> listRealtorValidatedDeals(User user);
    List<Deal> listBrokerNonValidated(User user);
    List<Deal> listRealtorNonValidatedDeals(User user);
}
