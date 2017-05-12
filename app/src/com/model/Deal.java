package com.model;

public class Deal extends Entity {

    private Offer offer;
    private User buyer;
    private User realtor;
    private User broker;
    private boolean validated;

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public User getRealtor() {
        return realtor;
    }

    public void setRealtor(User seller) {
        this.realtor = seller;
    }

    public User getBroker() {
        return broker;
    }

    public void setBroker(User broker) {
        this.broker = broker;
    }

    public boolean getValidated() {
        return this.validated;
    }

    public void setValidated(boolean validated) {
        this.validated = validated;
    }
}
