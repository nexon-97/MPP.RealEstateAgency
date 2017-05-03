package com.dao;

import com.model.Deal;
import com.model.User;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class DealDAOImpl extends BaseDAO implements DealDAO {

    @Override
    public boolean addDeal(Deal deal) {
        Session session = getSessionFactory().openSession();
        if (session != null) {
            try{
                Transaction tx = session.beginTransaction();
                session.save(deal);
                tx.commit();
            }
            catch (HibernateException e){
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    @Override
    public Deal getDealById(int id) {
        Deal deal = null;
        Session session = getSessionFactory().openSession();
        if (session != null) {
            try {
                Transaction tx = session.beginTransaction();
                deal = (Deal)session.get(Deal.class, id);
                tx.commit();
            } catch (HibernateException e){
                e.printStackTrace();
            }
        }

        return deal;
    }

    @Override
    public boolean update(Deal deal) {
        Session session = getSessionFactory().openSession();
        if (session != null) {
            try {
                Transaction tx = session.beginTransaction();
                session.update(deal);
                tx.commit();

                return true;
            } catch (HibernateException e){
                e.printStackTrace();
            }
        }

        return false;
    }

    @Override
    public List<Deal> listValidated() {
        Session session = openSession();
        if (session != null) {
            try{
                Transaction tx = session.beginTransaction();
                Criteria criteria = session.createCriteria(Deal.class).add(Restrictions.eq("validated", 1));
                List<Deal> deals = criteria.list();
                tx.commit();

                return deals;
            }
            catch (HibernateException e){
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    public List<Deal> listNonValidated() {
        Session session = openSession();
        if (session != null) {
            try{
                Transaction tx = session.beginTransaction();
                Criteria criteria = session.createCriteria(Deal.class).add(Restrictions.eq("validated", 0));
                List<Deal> deals = criteria.list();
                tx.commit();

                return deals;
            }
            catch (HibernateException e){
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    public List<Deal> list() {
        Session session = openSession();
        if (session != null) {
            try{
                Transaction tx = session.beginTransaction();
                List<Deal> deals = session.createCriteria(Deal.class).list();
                tx.commit();

                return deals;
            }
            catch (HibernateException e){
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    public List<Deal> listBrokerValidatedDeals(User user) {
        Session session = openSession();
        if (session != null) {
            try {
                Transaction tx = session.beginTransaction();
                Criteria criteria = session.createCriteria(Deal.class)
                        .add(Restrictions.eq("broker", user))
                        .add(Restrictions.eq("validated", true));
                List<Deal> deals = criteria.list();
                tx.commit();

                return deals;
            } catch (HibernateException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    public List<Deal> listRealtorValidatedDeals(User user) {
        Session session = openSession();
        if (session != null) {
            try {
                Transaction tx = session.beginTransaction();
                Criteria criteria = session.createCriteria(Deal.class)
                        .add(Restrictions.eq("realtor", user))
                        .add(Restrictions.eq("validated", true));
                List<Deal> deals = criteria.list();
                tx.commit();

                return deals;
            } catch (HibernateException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    public List<Deal> listBrokerNonValidated(User user) {
        Session session = openSession();
        if (session != null) {
            try {
                Transaction tx = session.beginTransaction();
                Criteria criteria = session.createCriteria(Deal.class)
                        .add(Restrictions.isNull("broker"))
                        .add(Restrictions.eq("validated", false));
                List<Deal> deals = criteria.list();
                tx.commit();

                return deals;
            } catch (HibernateException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    public List<Deal> listRealtorNonValidatedDeals(User user) {
        Session session = openSession();
        if (session != null) {
            try {
                Transaction tx = session.beginTransaction();
                Criteria criteria = session.createCriteria(Deal.class)
                        .add(Restrictions.eq("realtor", null))
                        .add(Restrictions.eq("validated", false));
                List<Deal> deals = criteria.list();
                tx.commit();

                return deals;
            } catch (HibernateException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
