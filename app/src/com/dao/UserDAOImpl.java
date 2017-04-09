package com.dao;

import com.model.User;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class UserDAOImpl implements UserDAO {
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Override
    public User getById(int id) {
        Session session = this.sessionFactory.openSession();
        session.beginTransaction();

        User user = (User)session.get(User.class, id);

        session.getTransaction().commit();
        session.close();

        return user;
    }

    @Override
    public User getByLogin(String login) {
        Session session = this.sessionFactory.openSession();
        User user = null;

        try {
            Transaction tx = session.beginTransaction();

            Criteria criteria = session.createCriteria(User.class);
            criteria.add(Restrictions.eq("login", login));
            user = (User) criteria.uniqueResult();

            tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }

        return user;
    }

    @Override
    public boolean save(User user) {
        return false;
    }

    @Override
    public boolean update(User user) {
        return false;
    }

    @Override
    public List<User> list() {
        return null;
    }
}
