package com.dao.impl;

import com.dao.AbstractCrudDAO;
import com.dao.DealDAO;
import com.model.*;
import org.hibernate.*;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class DealDAOImpl extends AbstractCrudDAO<Deal> implements DealDAO {

    public DealDAOImpl() {
        super(Deal.class);
    }

    @Override
    public List<Deal> listSigned() {
        Session session = getSession();
        if (session != null) {
            try{
                Transaction tx = session.beginTransaction();
                Criteria criteria = session.createCriteria(Deal.class).add(Restrictions.eq("validated", true));
                List<Deal> deals = criteria.list();
                tx.commit();

                return deals;
            }
            catch (HibernateException e){
                e.printStackTrace();
            } finally {
                closeSession();
            }
        }

        return null;
    }

    @Override
    public List<Deal> listUnsigned() {
        Session session = getSession();
        if (session != null) {
            try{
                Transaction tx = session.beginTransaction();
                Criteria criteria = session.createCriteria(Deal.class).add(Restrictions.eq("validated", false));
                List<Deal> deals = criteria.list();
                tx.commit();

                return deals;
            }
            catch (HibernateException e){
                e.printStackTrace();
            } finally {
                closeSession();
            }
        }

        return null;
    }

    @Override
    public List<Deal> listOfferDeals(Offer offer){
        Session session = getSession();
        try {
            Transaction tx = session.beginTransaction();
            Criteria roleCriteria = session.createCriteria(Deal.class)
                    .add(Restrictions.eq("offer", offer));
            List<Deal> deals = roleCriteria.list();
            tx.commit();

            return deals;
        } catch (HibernateException e) {
            if (session.getTransaction() != null) session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            closeSession();
        }

        return null;
    }

    @Override
    public List<Deal> listBrokerDeals(User broker) {
        Session session = getSession();
        if (session != null) {
            Criteria criteria = session.createCriteria(Deal.class)
                    .add(Restrictions.eq("broker", broker))
                    .add(Restrictions.eq("validated", true));

            return filter(criteria);
        }

        return null;
    }

    @Override
    public List<Deal> listRealtorValidatedDeals(User realtor) {
        Session session = getSession();
        if (session != null) {
            Criteria criteria = session.createCriteria(Deal.class)
                    .add(Restrictions.eq("realtor", realtor))
                    .add(Restrictions.eq("validated", true));

            return filter(criteria);
        }

        return null;
    }
}
