package com.services.impl;

import com.dao.OfferDAO;
import com.dao.impl.OfferDAOImpl;
import com.helper.SystemMessages;
import com.model.Offer;
import com.model.Property;
import com.model.User;
import com.services.AuthService;
import com.services.OfferService;
import com.services.PermissionService;
import com.services.shared.*;
import com.utils.request.filter.FilterParameter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class OfferServiceImpl extends BaseService implements OfferService {

    @Autowired
    AuthService authService;

    @Autowired
    PermissionService permissionService;

    @Autowired
    OfferDAO offerDAO;

    @Override
    public List<Offer> getUserOffers(User user) {
        return offerDAO.listUserOffers(user);
    }

    @Override
    public List<Offer> listAllOffers() {
        return offerDAO.list();
    }

    @Override
    public Offer getOfferById(int id) {
        return offerDAO.get(id);
    }

    @Override
    public boolean addOffer(Offer offer) {
        if (isValid(offer)) {
            User loggedUser = authService.getLoggedUser();

            if (Objects.equals(loggedUser, offer.getProperty().getOwner())) {
                return offerDAO.add(offer);
            } else {
                setErrorInfo(SystemMessages.UserIsNotOfferOwnerMessage);
            }
        } else {
            setErrorInfo(SystemMessages.UnacceptableOfferParams);
        }

        return false;
    }

    @Override
    public boolean deleteOffer(Offer offer) {
        User loggedUser = authService.getLoggedUser();
        boolean hasPermission = permissionService.canDeleteOffer(loggedUser, offer);

        if (hasPermission) {
            return offerDAO.delete(offer);
        }

        return false;
    }

    @Override
    public boolean updateOffer(Offer offer) {
        User loggedUser = authService.getLoggedUser();
        boolean hasPermission = permissionService.canEditOffer(loggedUser, offer);

        if (hasPermission && isValid(offer)) {
            return offerDAO.update(offer);
        }

        return false;
    }

    @Override
    public List<Offer> filterOffers(List<FilterParameter> filterParameters) {
        return offerDAO.filter(filterParameters);
    }

    @Override
    public boolean isValid(Offer offer) {
        boolean costPositive = offer.getCost().compareTo(BigDecimal.ZERO) >= 0;
        boolean propertyExists = offer.getProperty() != null;
        boolean offerTypeNotNull = offer.getOfferType() != null;

        return costPositive && propertyExists && offerTypeNotNull;
    }

    @Override
    public boolean hasOfferOnProperty(Property property) {
        List<Offer> offers = offerDAO.listPropertyOffers(property);
        return offers.size() != 0;
    }
}
