package com.dao;

import com.model.*;

import java.util.List;

public interface DealDAO extends CrudDAO<Deal> {
    List<Deal> listSigned();
    List<Deal> listUnsigned();
    List<Deal> listBrokerDeals(User broker);
    List<Deal> listRealtorValidatedDeals(User realtor);
    List<Deal> listOfferDeals(Offer offer);
}
