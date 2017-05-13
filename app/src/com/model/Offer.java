package com.model;

import java.math.BigDecimal;

public class Offer extends Entity {

    private OfferType offerType;
    private Property property;
    private BigDecimal cost;
    private boolean outdated;

    public void setOfferType(OfferType offerType) {
        this.offerType = offerType;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public void setOutdated(boolean outdated) {
        this.outdated = outdated;
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

    public boolean getOutdated() {
        return this.outdated;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!this.getClass().equals(obj.getClass())) return false;

        Offer obj2 = (Offer)obj;
        return ((this.id == obj2.getId()));
    }

    @Override
    public boolean isOwner(User user) {
        return property.getOwner().equals(user);
    }
}
