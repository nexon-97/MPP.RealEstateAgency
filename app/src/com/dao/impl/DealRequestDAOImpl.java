package com.dao.impl;

import com.dao.AbstractCrudDAO;
import com.dao.DealRequestDAO;
import com.model.*;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DealRequestDAOImpl extends AbstractCrudDAO<DealRequest> implements DealRequestDAO {

    public DealRequestDAOImpl() {
        super(DealRequest.class);
    }

    @Override
    public List<DealRequest> listUncommittedRealtorRequests(User realtor) {
        Session session = getSession();
        if (session != null) {
            Criteria criteria = session.createCriteria(DealRequest.class)
                    .add(Restrictions.eq("realtor", realtor))
                    .add(Restrictions.eq("realtorValidation", false));

            return filter(criteria);
        }

        return null;
    }

    @Override
    public List<DealRequest> listUncommittedBuyerRequests() {
        Session session = getSession();
        if (session != null) {
            Criteria criteria = session.createCriteria(DealRequest.class)
                    .add(Restrictions.eq("buyerValidation", false));

            return filter(criteria);
        }

        return null;
    }

    @Override
    public List<DealRequest> listUncommittedSellerRequests(User seller) {
        Session session = getSession();
        if (session != null) {
            SQLQuery query = session.createSQLQuery("SELECT {r.*}, {o.*}, {p.*} FROM deal_request r JOIN Offer o ON r.offer_id = o.offer_id JOIN Property p ON o.property_id = p.property_id WHERE p.owner = :owner and r.seller_confirm = 0");

            Map<String, Object> queryParams = new HashMap<>();
            queryParams.put("owner", seller);

            Map<String, Class<? extends Entity>> entityClasses = new HashMap<>();
            entityClasses.put("r", DealRequest.class);
            entityClasses.put("o", Offer.class);
            entityClasses.put("p", Property.class);

            Map<String, String> joins = new HashMap<>();
            joins.put("o", "r.offer");
            joins.put("p", "o.property");

            return filter(query, queryParams, entityClasses, joins);
        }

        return null;
    }

    @Override
    public boolean checkDealRequestClone(DealRequest request) {
        Session session = getSession();
        if (session != null) {
            Criteria criteria = session.createCriteria(DealRequest.class)
                    .add(Restrictions.eq("offer", request.getOffer()))
                    .add(Restrictions.eq("buyer", request.getBuyer()));

            return filter(criteria).size() > 0;
        }

        return false;
    }
}
