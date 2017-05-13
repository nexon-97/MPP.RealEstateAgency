package com.dao;

import com.model.Offer;
import com.model.Property;
import com.model.User;
import com.utils.request.filter.FilterParameter;

import java.util.List;

public interface OfferDAO extends CrudDAO<Offer> {
    List<Offer> listUserOffers(User user);
    List<Offer> listPropertyOffers(Property property);
    List<Offer> filter(List<FilterParameter> filterParams);
    List<Offer> listActual();
}
