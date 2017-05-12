package com.services;

import com.model.Deal;
import com.model.Offer;
import com.model.User;

import java.util.List;

public interface DealService extends CrudService<Deal> {
    boolean signDeal(Deal deal, User broker);
    List<Deal> listValidatedDeals();
    List<Deal> listUnsignedDeals();
    List<Deal> listBrokerDeals(User broker);
    List<Deal> listRealtorDeals(User realtor);
    boolean hasDealOnOffer(Offer offer);
}
