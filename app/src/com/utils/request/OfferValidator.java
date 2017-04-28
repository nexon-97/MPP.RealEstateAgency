package com.utils.request;

import com.model.Offer;
import com.model.OfferType;
import com.services.shared.ServiceManager;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Map;

public class OfferValidator implements Verifiable {

    private boolean parsingValid;
    private Offer offer;

    public OfferValidator(Map<String, String[]> params) {
        String propertyIdStr;
        String offerTypeStr;
        String costStr;

        try {
            propertyIdStr = params.get("property")[0];
            offerTypeStr = params.get("offerType")[0];
            costStr = params.get("cost")[0];
        } catch (NullPointerException e) {
            return;
        }

        offer = new Offer();

        try {
            String idStr = params.get("id")[0];
            int requestId = Integer.valueOf(idStr);
            offer.setId(requestId);
        } catch (NullPointerException | NumberFormatException e) {}

        parsingValid = parsePropertyId(propertyIdStr) && parseOfferType(offerTypeStr) && parseCost(costStr);
    }

    public Offer getOffer() {
        return offer;
    }

    private boolean parsePropertyId(String idStr) {
        try {
            int propertyId = Integer.valueOf(idStr);
            offer.setProperty(ServiceManager.getInstance().getPropertyService().getPropertyById(propertyId));

            return true;
        } catch (NullPointerException | NumberFormatException e) {
            return false;
        }
    }

    private boolean parseOfferType(String offerTypeStr) {
        try {
            OfferType offerType = OfferType.valueOf(offerTypeStr);
            offer.setOfferType(offerType);

            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private boolean parseCost(String costStr) {
        try {
            DecimalFormatSymbols symbols = new DecimalFormatSymbols();
            symbols.setDecimalSeparator('.');
            String pattern = "#0.0#";
            DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
            decimalFormat.setParseBigDecimal(true);

            BigDecimal cost = (BigDecimal) decimalFormat.parse(costStr);
            offer.setCost(cost);

            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    @Override
    public boolean verify() {
        if (offer == null)
            return false;

        boolean costValid = offer.getCost().compareTo(BigDecimal.ZERO) >= 0;
        return parsingValid && costValid;
    }
}
