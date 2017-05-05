package com.services;

import com.model.DealRequest;
import com.model.Offer;
import com.model.User;
import com.utils.request.filter.FilterParameter;

import java.util.List;

public interface OfferService {
    List<Offer> getUserOffers(User user);
    List<Offer> listAllOffers();
    Offer getOfferById(int id);
    boolean addOffer(Offer offer);
    boolean deleteOffer(Offer offer);
    boolean updateOffer(Offer offer);
    List<Offer> filterOffers(List<FilterParameter> filterParameters);
    boolean isValid(Offer offer);
}
