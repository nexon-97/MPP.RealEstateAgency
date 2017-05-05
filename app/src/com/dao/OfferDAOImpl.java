package com.dao;

import com.model.Offer;
import com.model.Property;
import com.model.User;
import com.utils.request.filter.FilterParameter;
import org.hibernate.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OfferDAOImpl extends BaseDAO implements OfferDAO {

    Map<Class<?>, String> classNames;

    public OfferDAOImpl() {
        classNames = new HashMap<>();
        classNames.put(Offer.class, "o");
        classNames.put(Property.class, "p");
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
    public List<Offer> filter(List<FilterParameter> filterParams) {
        Session session = openSession();
        if (session != null) {
            try {
                Transaction tx = session.beginTransaction();

                String queryStr = constructFilterQuery(filterParams);
                Query safeQuery = session.createSQLQuery(queryStr)
                        .addEntity(classNames.get(Offer.class), Offer.class)
                        .addJoin(classNames.get(Property.class), String.format("%s.property", classNames.get(Offer.class)));

                // Insert query values
                for (FilterParameter param : filterParams) {
                    param.fillQuery(safeQuery);
                }

                List rows = safeQuery.list();

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
