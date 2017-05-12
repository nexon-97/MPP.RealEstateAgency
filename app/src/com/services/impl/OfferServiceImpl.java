package com.services.impl;

import com.dao.OfferDAO;
import com.dao.impl.OfferDAOImpl;
import com.helper.SystemMessages;
import com.model.Offer;
import com.model.Property;
import com.model.User;
import com.services.OfferService;
import com.services.shared.*;
import com.utils.request.filter.FilterParameter;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class OfferServiceImpl extends BaseService implements OfferService {

    public OfferServiceImpl(ServiceSharedResources sharedResources) {
        super(ServiceId.OfferService, sharedResources);
    }

    @Override
    public List<Offer> getUserOffers(User user) {
        OfferDAO offerDAO = new OfferDAOImpl();

        return offerDAO.listUserOffers(user);
    }

    @Override
    public List<Offer> listAllOffers() {
        OfferDAO offerDAO = new OfferDAOImpl();

        return offerDAO.list();
    }

    @Override
    public Offer getOfferById(int id) {
        OfferDAO offerDAO = new OfferDAOImpl();

        return offerDAO.get(id);
    }

    @Override
    public boolean addOffer(Offer offer) {
        if (isValid(offer)) {
            User loggedUser = ServiceManager.getInstance().getAuthService().getLoggedUser();

            if (Objects.equals(loggedUser, offer.getProperty().getOwner())) {
                OfferDAO offerDAO = new OfferDAOImpl();
                return offerDAO.add(offer);
            } else {
                setErrorInfo(HttpServletResponse.SC_FORBIDDEN, SystemMessages.UserIsNotOfferOwnerMessage);
            }
        } else {
            setErrorInfo(HttpServletResponse.SC_BAD_REQUEST, SystemMessages.UnacceptableOfferParams);
        }

        return false;
    }

    @Override
    public boolean deleteOffer(Offer offer) {
        User loggedUser = ServiceManager.getInstance().getAuthService().getLoggedUser();
        boolean hasPermission = ServiceManager.getInstance().getPermissionService().canDeleteOffer(loggedUser, offer);

        if (hasPermission) {
            OfferDAO offerDAO = new OfferDAOImpl();
            return offerDAO.delete(offer);
        }

        return false;
    }

    @Override
    public boolean updateOffer(Offer offer) {
        User loggedUser = ServiceManager.getInstance().getAuthService().getLoggedUser();
        boolean hasPermission = ServiceManager.getInstance().getPermissionService().canEditOffer(loggedUser, offer);

        if (hasPermission && isValid(offer)) {
            OfferDAO offerDAO = new OfferDAOImpl();
            return offerDAO.update(offer);
        }

        return false;
    }

    @Override
    public List<Offer> filterOffers(List<FilterParameter> filterParameters) {
        OfferDAO dao = new OfferDAOImpl();
        return dao.filter(filterParameters);
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
}
