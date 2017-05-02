package com.dao;

import com.model.Offer;
import com.model.User;
import com.utils.request.filter.FilterParameter;

import java.util.List;
import java.util.Map;

public interface OfferDAO {
    Offer getOfferById(int id);
    boolean updateOffer(Offer offer);
    boolean deleteOffer(Offer offer);
    boolean addOffer(Offer offer);
    List<Offer> listUserOffers(User user);
    List<Offer> list();
    List<Offer> filter(List<FilterParameter> filterParams);
}
