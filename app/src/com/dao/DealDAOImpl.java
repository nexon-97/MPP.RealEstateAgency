package com.dao;

import com.model.*;
import org.hibernate.*;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.engine.internal.JoinSequence;

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
    public List<Deal> listOfferDeals(Offer offer){
        Session session = openSession();
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
            session.close();
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

    @Override
    public boolean addDealRequest(DealRequest request) {
        Session session = getSessionFactory().openSession();
        if (session != null) {
            try {
                Transaction tx = session.beginTransaction();
                session.save(request);
                tx.commit();

                return true;
            } catch (HibernateException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    @Override
    public boolean deleteDealRequest(DealRequest request) {
        Session session = getSessionFactory().openSession();
        if (session != null) {
            try {
                Transaction tx = session.beginTransaction();
                session.delete(request);
                tx.commit();

                return true;
            } catch (HibernateException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    @Override
    public boolean updateDealRequest(DealRequest request) {
        Session session = getSessionFactory().openSession();
        if (session != null) {
            try {
                Transaction tx = session.beginTransaction();
                session.update(request);
                tx.commit();

                return true;
            } catch (HibernateException e){
                e.printStackTrace();
            }
        }

        return false;
    }

    @Override
    public List<DealRequest> listDealRequests() {
        Session session = openSession();
        if (session != null) {
            try{
                Transaction tx = session.beginTransaction();
                List<DealRequest> requests = session.createCriteria(DealRequest.class).list();
                tx.commit();

                return requests;
            }
            catch (HibernateException e){
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    public boolean isDealRequestAlreadyAdded(DealRequest request) {
        Session session = openSession();
        if (session != null) {
            try{
                Transaction tx = session.beginTransaction();
                Criteria criteria = session.createCriteria(DealRequest.class)
                    .add(Restrictions.eq("buyer", request.getBuyer()))
                    .add(Restrictions.eq("offer", request.getOffer()));
                DealRequest dbRequest = (DealRequest)criteria.uniqueResult();
                tx.commit();

                return dbRequest != null;
            }
            catch (HibernateException e){
                e.printStackTrace();
            }
        }

        return false;
    }

    @Override
    public List<DealRequest> listUncommittedRealtorRequests(User realtor) {
        Session session = openSession();
        if (session != null) {
            try{
                Transaction tx = session.beginTransaction();
                Criteria criteria = session.createCriteria(DealRequest.class)
                        .add(Restrictions.eq("realtor", realtor))
                        .add(Restrictions.eq("realtorValidation", false));
                List<DealRequest> requests = criteria.list();
                tx.commit();

                return requests;
            }
            catch (HibernateException e){
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    public List<DealRequest> listUncommittedBuyerRequests(User buyer) {
        Session session = openSession();
        if (session != null) {
            try{
                Transaction tx = session.beginTransaction();
                Criteria criteria = session.createCriteria(DealRequest.class)
                        .add(Restrictions.eq("buyer", buyer))
                        .add(Restrictions.eq("buyerValidation", false));
                List<DealRequest> requests = criteria.list();
                tx.commit();

                return requests;
            }
            catch (HibernateException e){
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    public List<DealRequest> listUncommittedSellerRequests(User seller) {
        /*Session session = openSession();
        if (session != null) {
            try{
                Transaction tx = session.beginTransaction();

                Criteria criteria = session.createCriteria(DealRequest.class)
                        .add("offer", "o", Criteria.LEFT_JOIN)
                        .add(Restrictions.eq("offer.property.owner", seller));

                List<DealRequest> requests = criteria.list();
                tx.commit();

                return requests;
            }
            catch (HibernateException e){
                e.printStackTrace();
            }
        }*/

        return null;
    }

    @Override
    public DealRequest getDealRequestById(int id) {
        Session session = openSession();
        if (session != null) {
            try {
                Transaction tx = session.beginTransaction();
                DealRequest request = (DealRequest)session.get(DealRequest.class, id);
                tx.commit();

                return request;
            } catch (HibernateException e){
                e.printStackTrace();
            }
        }

        return null;
    }
}
