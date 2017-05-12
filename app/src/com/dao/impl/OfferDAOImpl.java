package com.dao.impl;

import com.dao.AbstractCrudDAO;
import com.dao.OfferDAO;
import com.model.Entity;
import com.model.Offer;
import com.model.Property;
import com.model.User;
import com.utils.request.filter.FilterParameter;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OfferDAOImpl extends AbstractCrudDAO<Offer> implements OfferDAO {

    private Map<Class<?>, String> classNames;

    public OfferDAOImpl() {
        super(Offer.class);

        classNames = new HashMap<>();
        classNames.put(Offer.class, "o");
        classNames.put(Property.class, "p");
    }

    @Override
    public List<Offer> listPropertyOffers(Property property) {
        Session session = getSession();
        if (session != null) {
            Criteria propertyCriteria = session.createCriteria(Offer.class)
                    .add(Restrictions.eq("property", property));

            return filter(propertyCriteria);
        }

        return null;
    }

    @Override
    public List<Offer> listUserOffers(User user) {
        Session session = getSession();
        if (session != null) {
            SQLQuery query = session.createSQLQuery("SELECT {o.*}, {p.*} FROM Offer o JOIN Property p ON o.property_id = p.property_id WHERE p.owner = :owner");

            Map<String, Object> queryParams = new HashMap<>();
            queryParams.put("owner", user);

            Map<String, Class<? extends Entity>> entityClasses = new HashMap<>();
            entityClasses.put("o", Offer.class);

            Map<String, String> joins = new HashMap<>();
            joins.put("p", "o.property");

            return filter(query, queryParams, entityClasses, joins);
        }

        return null;
    }

    @Override
    public List<Offer> filter(List<FilterParameter> filterParams) {
        Session session = getSession();
        if (session != null) {
            String queryStr = constructFilterQuery(filterParams);
            SQLQuery safeQuery = session.createSQLQuery(queryStr)
                    .addEntity(classNames.get(Offer.class), Offer.class)
                    .addJoin(classNames.get(Property.class), String.format("%s.property", classNames.get(Offer.class)));

            // Insert query values
            for (FilterParameter param : filterParams) {
                param.fillQuery(safeQuery);
            }

            return filter(safeQuery);
        }

        return null;
    }

    private String constructFilterQuery(List<FilterParameter> filterParams) {
        String o = classNames.get(Offer.class);
        String p = classNames.get(Property.class);
        String query = String.format("SELECT {%s.*}, {%s.*} FROM Offer %s JOIN Property %s ON %s.property_id = %s.property_id", o, p, o, p, o, p);

        boolean isFirst = true;
        for (FilterParameter param : filterParams) {
            if (param.isActive()) {
                if (isFirst) {
                    query = query + " WHERE ";
                } else {
                    query = query + " and ";
                }

                String entityName = classNames.get(param.getParamClass());
                query = query + param.getFilterQuery(entityName);
                isFirst = false;
            }
        }

        return query;
    }
}
