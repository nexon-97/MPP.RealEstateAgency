package com.model;


import java.util.Date;

public class Document {
    private int id;
    private int documentTypeID;
    private int buyerID;
    private int sellerID;
    private int offerID;
    private Date confirmDate;
    private Date graduationDate;

    public Date getGraduationDate() {
        return graduationDate;
    }

    public int getDocumentTypeID() {
        return documentTypeID;
    }

    public int getBuyerID() {
        return buyerID;
    }

    public int getSellerID() {
        return sellerID;
    }

    public int getOfferID() {
        return offerID;
    }

    public Date getConfirmDate() {
        return confirmDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDocumentTypeID(int documentTypeID) {
        this.documentTypeID = documentTypeID;
    }

    public void setBuyerID(int buyerID) {
        this.buyerID = buyerID;
    }

    public void setSellerID(int sellerID) {
        this.sellerID = sellerID;
    }

    public void setOfferID(int offerID) {
        this.offerID = offerID;
    }

    public void setGraduationDate(Date graduationDate) {
        this.graduationDate = graduationDate;
    }

    public void setConfirmDate(Date confirmDate) {
        this.confirmDate = confirmDate;
    }

    public int getId() {
        return id;
    }

}
