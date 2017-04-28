package com.services;

import com.dao.OfferDAO;
import com.dao.OfferDAOImpl;
import com.model.Offer;
import com.model.RoleId;
import com.model.User;
import com.services.shared.*;

import java.util.List;

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

        return offerDAO.getOfferById(id);
    }

    @Override
    public boolean addOffer(Offer offer) {
        OfferDAO offerDAO = new OfferDAOImpl();

        return offerDAO.addOffer(offer);
    }

    @Override
    public boolean deleteOffer(Offer offer) {
        // Check delete offer permission
        User loggedUser = ServiceManager.getInstance().getAuthService().getLoggedUser();
        boolean isOwner = loggedUser != null && offer.getProperty().getOwner().equals(loggedUser);
        boolean isAdmin = loggedUser.getRole().getRoleId().equals(RoleId.Admin);
        if (isOwner || isAdmin) {
            OfferDAO offerDAO = new OfferDAOImpl();
            return offerDAO.deleteOffer(offer);
        }

        return false;
    }
}
