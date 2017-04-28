package com.model;


import java.util.Date;

public class Document {
    private int id;
    private DocumentType documentType;
    private User buyer;
    private User seller;
    private Offer offer;
    private Date confirmDate;
    private Date graduationDate;

    public int getId() {
        return id;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public User getBuyer() {
        return buyer;
    }

    public User getSeller() {
        return seller;
    }

    public Offer getOffer() {
        return offer;
    }

    public Date getGraduationDate() {
        return graduationDate;
    }

    public Date getConfirmDate() {
        return confirmDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    public void setGraduationDate(Date graduationDate) {
        this.graduationDate = graduationDate;
    }

    public void setConfirmDate(Date confirmDate) {
        this.confirmDate = confirmDate;
    }



}
