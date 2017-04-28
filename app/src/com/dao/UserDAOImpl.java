package com.dao;

import com.model.User;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class UserDAOImpl extends BaseDAO implements UserDAO {
    @Override
    public User getById(int id) {
        Session session = getSessionFactory().openSession();
        session.beginTransaction();

        User user = (User)session.get(User.class, id);

        session.getTransaction().commit();
        session.close();

        return user;
    }

    @Override
    public User getByLogin(String login) {
        Session session = openSession();
        User user = null;

        try {
            Transaction tx = session.beginTransaction();

            Criteria criteria = session.createCriteria(User.class);
            criteria.add(Restrictions.eq("login", login));
            user = (User) criteria.uniqueResult();

            tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return user;
    }

    @Override
    public boolean save(User user) {
        if (getByLogin(user.getLogin()) != null){
            return false;
        }
        Session  session = getSessionFactory().openSession();
        if (session != null) {
            try {
                Transaction tx = session.beginTransaction();
                session.save(user);
                tx.commit();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            } finally {
                session.close();
            }
        }
        return true;
    }

    @Override
    public boolean update(User user) {
        Session session = openSession();

        if (session != null) {
            try {
                Transaction tx = session.beginTransaction();

                session.update(user);

                tx.commit();
                return true;
            } catch (HibernateException e) {
                if (session.getTransaction() != null) session.getTransaction().rollback();
                e.printStackTrace();
            } finally {
                session.close();
            }
        }

        return false;
    }

    @Override
    public List<User> list() {
        Session session = openSession();
        List<User> users = null;

        try {
            Transaction tx = session.beginTransaction();
            users = (List<User>) session.createQuery("FROM User").list();
            tx.commit();
        } catch (HibernateException e) {
            if (session.getTransaction() != null) session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return users;
    }
}
