package com.dao;

import com.model.Deal;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

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
            try{
                Transaction tx = session.beginTransaction();
                deal = (Deal)session.get(Deal.class, id);
                tx.commit();
            }
            catch (HibernateException e){
                e.printStackTrace();
            }
        }
        return deal;
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
}
