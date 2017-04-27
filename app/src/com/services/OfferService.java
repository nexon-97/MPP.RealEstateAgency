package com.services;

import com.model.Offer;
import com.model.User;

import java.util.List;

public interface OfferService {
    List<Offer> getUserOffers(User user);
    Offer getOfferById(int id);
}
