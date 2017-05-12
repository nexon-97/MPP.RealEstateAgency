package com.model;

public class DealRequest extends Entity {

    private Offer offer;
    private User buyer;
    private User realtor;
    private boolean sellerValidation;
    private boolean buyerValidation;
    private Boolean realtorValidation;

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public void setRealtor(User realtor) {
        this.realtor = realtor;
    }

    public void setBuyerValidation(Boolean sellerValidation) {
        this.sellerValidation = sellerValidation;
    }

    public void setSellerValidation(Boolean buyerValidation) {
        this.buyerValidation = buyerValidation;
    }

    public void setRealtorValidation(Boolean realtorValidation) {
        this.realtorValidation = realtorValidation;
    }

    public Offer getOffer() {
        return this.offer;
    }

    public User getBuyer() {
        return this.buyer;
    }

    public User getRealtor() {
        return this.realtor;
    }

    public boolean getBuyerValidation() {
        return this.sellerValidation;
    }

    public boolean getSellerValidation() {
        return this.buyerValidation;
    }

    public Boolean getRealtorValidation() {
        return this.realtorValidation;
    }
}
