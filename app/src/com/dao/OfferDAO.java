package com.dao;

import com.model.Offer;
import com.model.User;

import java.util.List;

public interface OfferDAO {
    Offer getOfferById(int id);
    boolean updateOffer(Offer offer);
    boolean deleteOffer(Offer offer);
    boolean addOffer(Offer offer);
    List<Offer> listUserOffers(User user);
}
