package com.services;

import com.dao.OfferDAO;
import com.dao.OfferDAOImpl;
import com.model.Offer;
import com.model.User;
import com.services.shared.BaseService;
import com.services.shared.ServiceId;
import com.services.shared.ServiceSharedResources;

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
}
