package com.dao;

import com.model.Offer;
import com.model.Property;
import com.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class OfferDAOImpl extends BaseDAO implements OfferDAO {

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
}
