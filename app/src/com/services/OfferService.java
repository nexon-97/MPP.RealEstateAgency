package com.services;

import com.model.Offer;
import com.model.User;

import java.util.List;

public interface OfferService {
    List<Offer> getUserOffers(User user);
    List<Offer> listAllOffers();
    Offer getOfferById(int id);
    boolean addOffer(Offer offer);
    boolean deleteOffer(Offer offer);
}
