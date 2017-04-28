package com.dao;

import com.model.Transaction;
import com.model.User;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class TransactionDAOImpl extends BaseDAO implements TransactionDAO{
    @Override
    public Transaction getById(int id) {
        Session session = getSessionFactory().openSession();
        session.beginTransaction();

        Transaction transaction = (Transaction) session.get(Transaction.class, id);

        session.getTransaction().commit();
        session.close();

        return transaction;
    }

    @Override
    public List<Transaction> getIncomingList(User user) {
        Session session = openSession();
        List<Transaction> transactions = null;

        try {
            org.hibernate.Transaction tx = session.beginTransaction();

            Criteria criteria = session.createCriteria(Transaction.class);
            criteria.add(Restrictions.eq("seller", user));
            transactions = (List<Transaction>)criteria.list();

            tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return transactions;
    }

    @Override
    public List<Transaction> getOutgoingList(User user) {
        Session session = openSession();
        List<Transaction> transactions = null;

        try {
            org.hibernate.Transaction tx = session.beginTransaction();

            Criteria criteria = session.createCriteria(Transaction.class);
            criteria.add(Restrictions.eq("buyer", user));
            transactions = (List<Transaction>)criteria.list();

            tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return transactions;
    }

    @Override
    public boolean addTransaction(Transaction transaction) {
        Session session = openSession();
        if (session != null) {
            try {
                org.hibernate.Transaction tx = session.beginTransaction();
                session.save(transaction);
                tx.commit();
            } catch (HibernateException e) {
                e.printStackTrace();
                return false;
            } finally {
                session.close();
            }
        }
        return true;
    }

    @Override
    public List<Transaction> list() {
        Session session = openSession();
        List<Transaction> transactions = null;

        try {
            org.hibernate.Transaction tx = session.beginTransaction();
            transactions = (List<Transaction>) session.createQuery("FROM Transaction").list();
            tx.commit();
        } catch (HibernateException e) {
            if (session.getTransaction() != null) session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return transactions;
    }
}
