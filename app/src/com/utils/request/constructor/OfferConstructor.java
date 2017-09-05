package com.utils.request.constructor;

import com.model.Offer;
import com.model.OfferType;
import com.model.Property;

import java.math.BigDecimal;
import java.util.Map;

public class OfferConstructor implements EntityConstructor<Offer> {

    @Override
    public Offer construct(Map<String, Object> values) {
        Offer offer = new Offer();

        OfferType offerType = (OfferType)values.getOrDefault("offerType", null);
        Property property = (Property)values.getOrDefault("property", null);
        BigDecimal cost = (BigDecimal)values.getOrDefault("cost", null);

        offer.setOfferType(offerType);
        offer.setProperty(property);
        offer.setCost(cost);

        return offer;
    }
}
