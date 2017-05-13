package com.services;

import com.model.Offer;
import com.model.Property;
import com.model.User;
import com.utils.request.ErrorRegistry;
import com.utils.request.filter.FilterParameter;

import java.util.List;

public interface OfferService extends CrudService<Offer> {
    List<Offer> getUserOffers(User user);
    List<Offer> filter(List<FilterParameter> filterParameters);
    List<Offer> listActual();
    boolean isValid(Offer offer);
    boolean hasOfferOnProperty(Property property);
}
