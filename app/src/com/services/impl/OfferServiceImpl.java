package com.services.impl;

import com.dao.OfferDAO;
import com.dao.impl.OfferDAOImpl;
import com.model.*;
import com.services.OfferService;
import com.services.PropertyService;
import com.services.shared.*;
import com.utils.request.ResponseData;
import com.utils.request.filter.FilterParameter;
import com.utils.request.validator.CostParameterValidator;
import com.utils.request.validator.EnumParameterValidator;
import com.utils.request.validator.PropertyParameterValidator;
import com.utils.request.validator.RequestValidationChain;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class OfferServiceImpl extends AbstractCrudService<Offer> implements OfferService {

    private RequestValidationChain validationChain;

    public OfferServiceImpl(ServiceSharedResources sharedResources) {
        super(ServiceId.OfferService, sharedResources, Offer.class);

        this.validationChain = buildValidationChain();
    }

    @Override
    public boolean add(Offer offer) {
        if (this.validationChain.validate()) {
            return super.add(offer);
        } else {
            getResponseData().setStatus(HttpServletResponse.SC_BAD_REQUEST);
            getSharedResources().getModel().put("errors", this.validationChain.getErrorMessageMap());

            return false;
        }
    }

    @Override
    public boolean update(Offer offer) {
        if (this.validationChain.validate()) {
            return super.update(offer);
        } else {
            getResponseData().setStatus(HttpServletResponse.SC_BAD_REQUEST);
            getSharedResources().getModel().put("errors", this.validationChain.getErrorMessageMap());

            return false;
        }
    }

    @Override
    public List<Offer> getUserOffers(User user) {
        OfferDAO offerDAO = new OfferDAOImpl();
        return offerDAO.listUserOffers(user);
    }

    @Override
    public List<Offer> filter(List<FilterParameter> filterParameters) {
        OfferDAO dao = new OfferDAOImpl();
        return dao.filter(filterParameters);
    }

    @Override
    public List<Offer> listActual() {
        OfferDAO dao = new OfferDAOImpl();
        return dao.listActual();
    }

    @Override
    public boolean isValid(Offer offer) {
        boolean costPositive = offer.getCost().compareTo(BigDecimal.ZERO) >= 0;
        boolean propertyExists = offer.getProperty() != null;
        boolean offerTypeNotNull = offer.getOfferType() != null;

        return costPositive && propertyExists && offerTypeNotNull;
    }

    @Override
    public boolean hasOfferOnProperty(Property property){
        OfferDAO offerDAO = new OfferDAOImpl();
        List<Offer> offers = offerDAO.listPropertyOffers(property);
        if(offers.size()!=0) return true;
        return false;
    }

    @Override
    public boolean canAdd(Offer offer) {
        User currentUser = getLoggedUser();
        if (currentUser != null && currentUser.getRoleId().equals(RoleId.User)) {
            PropertyService propertyService = ServiceManager.getInstance().getPropertyService();
            List<Property> userProperties = propertyService.listUserOwnedProperties(currentUser);

            if (userProperties.size() > 0) {
                if (offer.getProperty() == null || offer.getProperty().getOwner().equals(currentUser)) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean canUpdate(Offer offer) {
        User currentUser = getLoggedUser();
        if (currentUser != null && offer != null) {
            Offer databaseOffer = super.get(offer.getId());

            if (databaseOffer != null && offer != null) {
                return databaseOffer.getProperty().getOwner().equals(currentUser);
            }
        }

        return false;
    }

    @Override
    public boolean canDelete(Offer offer) {
        User loggedUser = getLoggedUser();
        if (loggedUser != null) {
            return loggedUser.getRoleId().equals(RoleId.Admin) ||
                    (offer.getProperty() != null && offer.getProperty().getOwner().equals(loggedUser));
        }

        return false;
    }

    @Override
    public Offer constructFromRequest() {
        Offer offer = super.constructFromRequest();

        if (offer != null) {
            this.validationChain.validate();
            Map<String, Object> values = this.validationChain.getValidatedValues();

            OfferType offerType = (OfferType)values.getOrDefault("offerType", null);
            Property property = (Property)values.getOrDefault("property", null);
            BigDecimal cost = (BigDecimal)values.getOrDefault("cost", null);

            offer.setOfferType(offerType);
            offer.setProperty(property);
            offer.setCost(cost);
        }

        return offer;
    }

    private RequestValidationChain buildValidationChain() {
        return new RequestValidationChain()
                .addValidator(new CostParameterValidator("cost", false))
                .addValidator(new PropertyParameterValidator("property", false))
                .addValidator(new EnumParameterValidator<>(OfferType.class, "offerType", false));
    }
}
