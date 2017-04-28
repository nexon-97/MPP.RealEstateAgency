package com.dao;

import com.model.Offer;
import com.model.Property;
import com.model.User;
import com.utils.request.FilterParameter;
import com.utils.request.PropertyFilterParamId;
import org.hibernate.*;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OfferDAOImpl extends BaseDAO implements OfferDAO {

    private Map<PropertyFilterParamId, String> offerColumnMapping;
    private Map<PropertyFilterParamId, String> propertyColumnMapping;

    public OfferDAOImpl() {
        offerColumnMapping = new HashMap<>();
        propertyColumnMapping = new HashMap<>();

        offerColumnMapping.put(PropertyFilterParamId.Cost, "cost");
        offerColumnMapping.put(PropertyFilterParamId.OfferType, "offer_type_id");
        propertyColumnMapping.put(PropertyFilterParamId.Area, "area");
        propertyColumnMapping.put(PropertyFilterParamId.RoomCount, "room_count");
        propertyColumnMapping.put(PropertyFilterParamId.DistanceToSubway, "distance_to_subway");
        propertyColumnMapping.put(PropertyFilterParamId.DistanceToTransportStop, "distance_to_transport_stop");
        propertyColumnMapping.put(PropertyFilterParamId.HasFurniture, "has_furniture");
        propertyColumnMapping.put(PropertyFilterParamId.HasInternet, "has_internet");
        propertyColumnMapping.put(PropertyFilterParamId.HasTv, "has_phone");
        propertyColumnMapping.put(PropertyFilterParamId.HasPhone, "has_tv");
        propertyColumnMapping.put(PropertyFilterParamId.HasFridge, "has_fridge");
        propertyColumnMapping.put(PropertyFilterParamId.HasStove, "has_stove");
    }

    @Override
    public Offer getOfferById(int id) {
        Session session = openSession();
        if (session != null) {
            try {
                Transaction tx = session.beginTransaction();
                Offer offer = (Offer)session.get(Offer.class, id);
                tx.commit();

                return offer;
            } catch (HibernateException e) {
                e.printStackTrace();

            } finally {
                session.close();
            }
        }

        return null;
    }

    @Override
    public boolean updateOffer(Offer offer) {
        Session session = openSession();
        if (session != null) {
            try {
                Transaction tx = session.beginTransaction();
                session.update(offer);
                tx.commit();

                return true;
            } catch (HibernateException e) {
                e.printStackTrace();
            } finally {
                session.close();
            }
        }

        return false;
    }

    @Override
    public boolean deleteOffer(Offer offer) {
        Session session = openSession();
        if (session != null) {
            try {
                Transaction tx = session.beginTransaction();
                session.delete(offer);
                tx.commit();

                return true;
            } catch (HibernateException e) {
                e.printStackTrace();
            } finally {
                session.close();
            }
        }

        return false;
    }

    @Override
    public boolean addOffer(Offer offer) {
        Session session = openSession();
        if (session != null) {
            try {
                Transaction tx = session.beginTransaction();
                session.save(offer);
                tx.commit();

                return true;
            } catch (HibernateException e) {
                e.printStackTrace();
            } finally {
                session.close();
            }
        }

        return false;
    }

    @Override
    public List<Offer> listUserOffers(User user) {
        Session session = openSession();
        if (session != null) {
            try {
                Transaction tx = session.beginTransaction();

                String userId = String.valueOf(user.getId());
                SQLQuery query = session.createSQLQuery("SELECT {o.*}, {p.*} FROM Offer o JOIN Property p ON o.property_id = p.property_id WHERE p.owner = " + userId)
                        .addEntity("o", Offer.class)
                        .addJoin("p","o.property");
                List rows = query.list();

                List<Offer> filteredOffers = new ArrayList<>();
                for (Object row : rows) {
                    Object[] joinedRow = (Object[])row;
                    filteredOffers.add((Offer)joinedRow[0]);
                }

                tx.commit();

                return filteredOffers;
            } catch (HibernateException e) {
                e.printStackTrace();
            } finally {
                session.close();
            }
        }

        return null;
    }

    @Override
    public List<Offer> list() {
        Session session = openSession();
        if (session != null) {
            try {
                Transaction tx = session.beginTransaction();
                List<Offer> allOffers = session.createCriteria(Offer.class).list();
                tx.commit();

                return allOffers;
            } catch (HibernateException e) {
                e.printStackTrace();
            } finally {
                session.close();
            }
        }

        return null;
    }

    @Override
    public List<Offer> filter(Map<PropertyFilterParamId, FilterParameter> filterParams) {
        Session session = openSession();
        if (session != null) {
            try {
                Transaction tx = session.beginTransaction();

                String queryStr = constructFilterQuery(filterParams);
                SQLQuery query = session.createSQLQuery(queryStr)
                        .addEntity("o", Offer.class)
                        .addJoin("p", "o.property");
                List rows = query.list();

                List<Offer> filteredOffers = new ArrayList<>();
                for (Object row : rows) {
                    Object[] joinedRow = (Object[])row;
                    filteredOffers.add((Offer)joinedRow[0]);
                }

                tx.commit();

                return filteredOffers;
            } catch (HibernateException e) {
                e.printStackTrace();
            } finally {
                session.close();
            }
        }

        return null;
    }

    private String constructFilterQuery(Map<PropertyFilterParamId, FilterParameter> filterParams) {
        String query = "SELECT {o.*}, {p.*} FROM Offer o JOIN Property p ON o.property_id = p.property_id WHERE ";

        boolean isFirst = true;
        for (FilterParameter param : filterParams.values()) {
            if (param.verify()) {
                String column = offerColumnMapping.getOrDefault(param.getParamId(), null);
                if (column != null) {
                    if (!isFirst) {
                        query = query + " and ";
                    }

                    query = query + param.getFilterQuery("o", column);
                    isFirst = false;
                }

                column = propertyColumnMapping.getOrDefault(param.getParamId(), null);
                if (column != null) {
                    if (!isFirst) {
                        query = query + " and ";
                    }

                    query = query + param.getFilterQuery("p", column);
                    isFirst = false;
                }
            }
        }

        return query;
    }
}
