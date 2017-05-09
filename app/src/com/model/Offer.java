package com.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Currency;

public class Offer {

    private int id;
    private OfferType offerType;
    private Property property;
    private BigDecimal cost;

    public void setId(int id) {
        this.id = id;
    }

    public void setOfferType(OfferType offerType) {
        this.offerType = offerType;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public int getId() {
        return this.id;
    }

    public OfferType getOfferType() {
        return this.offerType;
    }

    public Property getProperty() {
        return this.property;
    }

    public BigDecimal getCost() {
        return this.cost;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!this.getClass().equals(obj.getClass())) return false;

        Offer obj2 = (Offer)obj;
        return ((this.id == obj2.getId()));
    }
}
