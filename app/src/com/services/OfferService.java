package com.services;

import com.model.Offer;
import com.model.User;
import com.utils.request.FilterParameter;
import com.utils.request.PropertyFilterParamId;

import java.util.List;
import java.util.Map;

public interface OfferService {
    List<Offer> getUserOffers(User user);
    List<Offer> listAllOffers();
    Offer getOfferById(int id);
    boolean addOffer(Offer offer);
    boolean deleteOffer(Offer offer);
    boolean updateOffer(Offer offer);
    List<Offer> filterOffers(Map<PropertyFilterParamId, FilterParameter> filterParameters);
}
