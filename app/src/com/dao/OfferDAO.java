package com.dao;

import com.model.Offer;
import com.model.Property;
import com.model.User;
import com.utils.request.FilterParameter;
import com.utils.request.PropertyFilterParamId;

import java.util.List;
import java.util.Map;

public interface OfferDAO {
    Offer getOfferById(int id);
    boolean updateOffer(Offer offer);
    boolean deleteOffer(Offer offer);
    boolean addOffer(Offer offer);
    List<Offer> listUserOffers(User user);
    List<Offer> list();
    List<Offer> filter(Map<PropertyFilterParamId, FilterParameter> filterParams);
}
