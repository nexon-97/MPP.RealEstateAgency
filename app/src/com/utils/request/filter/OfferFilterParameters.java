package com.utils.request.filter;

import com.model.Offer;
import com.model.OfferType;
import com.model.Property;
import com.utils.request.validator.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OfferFilterParameters {

    RequestValidationChain validatorChain;

    public OfferFilterParameters() {
        validatorChain = buildValidationChain();
    }

    private RequestValidationChain buildValidationChain() {
        return new RequestValidationChain()
                .addValidator(new BigDecimalParameterValidator("costMin", true))
                .addValidator(new BigDecimalParameterValidator("costMax", true))
                .addValidator(new EnumParameterValidator<>(OfferType.class, "offerType", true))
                .addValidator(new IntegerParameterValidator("areaMin", true))
                .addValidator(new IntegerParameterValidator("areaMax", true))
                .addValidator(new IntegerParameterValidator("roomCountMin", true))
                .addValidator(new IntegerParameterValidator("roomCountMax", true))
                .addValidator(new IntegerParameterValidator("distanceToSubwayMin", true))
                .addValidator(new IntegerParameterValidator("distanceToSubwayMax", true))
                .addValidator(new IntegerParameterValidator("distanceToTransportStopMin", true))
                .addValidator(new IntegerParameterValidator("distanceToTransportStopMax", true));
    }

    public boolean validate(HttpServletRequest request) {
        return validatorChain.validate(request);
    }

    public List<FilterParameter> construct() {
        List<FilterParameter> filterParametersAll = new ArrayList<>();

        filterParametersAll.add(new BigDecimalRangeParameter(Offer.class, "cost",
                (BigDecimal) validatorChain.getValue("costMin"), (BigDecimal) validatorChain.getValue("costMax")));
        filterParametersAll.add(new EnumParameter<>("offer_type_id", (OfferType) validatorChain.getValue("offerType")));
        filterParametersAll.add(new IntegerRangeParameter(Property.class, "area",
                (Integer) validatorChain.getValue("areaMin"), (Integer) validatorChain.getValue("areaMax")));
        filterParametersAll.add(new IntegerRangeParameter(Property.class, "room_count",
                (Integer) validatorChain.getValue("roomCountMin"), (Integer) validatorChain.getValue("roomCountMax")));
        filterParametersAll.add(new IntegerRangeParameter(Property.class, "distance_to_subway",
                (Integer) validatorChain.getValue("distanceToSubwayMin"), (Integer) validatorChain.getValue("distanceToSubwayMax")));
        filterParametersAll.add(new IntegerRangeParameter(Property.class, "distance_to_transport_stop",
                (Integer) validatorChain.getValue("distanceToTransportStopMin"), (Integer) validatorChain.getValue("distanceToTransportStopMax")));
        filterParametersAll.add(new BooleanParameter(Property.class, "has_furniture", (Boolean) validatorChain.getValue("furniture")));
        filterParametersAll.add(new BooleanParameter(Property.class, "has_tv", (Boolean) validatorChain.getValue("tv")));
        filterParametersAll.add(new BooleanParameter(Property.class, "has_internet", (Boolean) validatorChain.getValue("internet")));
        filterParametersAll.add(new BooleanParameter(Property.class, "has_phone", (Boolean) validatorChain.getValue("phone")));
        filterParametersAll.add(new BooleanParameter(Property.class, "has_stove", (Boolean) validatorChain.getValue("stove")));
        filterParametersAll.add(new BooleanParameter(Property.class, "has_fridge", (Boolean) validatorChain.getValue("fridge")));

        // Throw away useless parameters
        List<FilterParameter> result = new ArrayList<>();
        for (FilterParameter param : filterParametersAll) {
            if (param.isActive()) {
                result.add(param);
            }
        }

        return result;
    }

    public Map<String, Object> getParamsMap() {
        return validatorChain.getValidatedValues();
    }
}
