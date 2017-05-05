package com.dao;

import com.model.RoleId;
import com.model.User;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class UserDAOImpl extends BaseDAO implements UserDAO {
    @Override
    public User getById(int id) {
        User user = null;
        Session session = getSessionFactory().openSession();

        if (session != null) {
            try {
                Transaction tx = session.beginTransaction();
                user = (User) session.get(User.class, id);
                tx.commit();
            } catch (HibernateException e) {
                e.printStackTrace();
                return null;
            } finally {
                session.close();
            }
        }
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
    public List<User> getUsersByRole(RoleId roleId) {
        Session session = openSession();
        try {
            Transaction tx = session.beginTransaction();
            Criteria roleCriteria = session.createCriteria(User.class)
                    .add(Restrictions.eq("roleId", roleId));
            List<User> users = roleCriteria.list();
            tx.commit();

            return users;
        } catch (HibernateException e) {
            if (session.getTransaction() != null) session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return null;
    }

    @Override
    public boolean save(User user) {
        if (getByLogin(user.getLogin()) != null) {
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
        try {
            Transaction tx = session.beginTransaction();
            List<User> users = session.createCriteria(User.class).list();
            tx.commit();

            return users;
        } catch (HibernateException e) {
            if (session.getTransaction() != null) session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return null;
    }
}
