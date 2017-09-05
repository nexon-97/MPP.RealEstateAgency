package com.dao.impl;

import com.dao.BaseDAO;
import com.dao.UserDAO;
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
                closeSession();
            }
        }
        return user;
    }

    @Override
    public User getByLogin(String login) {
        Session session = getSession();
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
            closeSession();
        }

        return user;
    }

    @Override
    public List<User> getUsersByRole(RoleId roleId) {
        Session session = getSession();
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
            closeSession();
        }

        return null;
    }

    @Override
    public boolean save(User user) {
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
                closeSession();
            }
        }
        return true;
    }

    @Override
    public boolean update(User user) {
        Session session = getSession();

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
                closeSession();
            }
        }

        return false;
    }

    public List<User> getSeveralUsers(int from, int count){
        Session session = getSession();
        List<User> users = null;

        try {
            Transaction tx = session.beginTransaction();

            Criteria filterCriteria = session.createCriteria(User.class)
                            .setFirstResult(from)
                            .setMaxResults(count);
            users = (List<User>)filterCriteria.list();

            tx.commit();
        } catch (HibernateException e) {
            if (session.getTransaction() != null) session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            closeSession();
        }

        return users;
    }

    @Override
    public List<User> list() {
        Session session = getSession();
        try {
            Transaction tx = session.beginTransaction();
            List<User> users = session.createCriteria(User.class).list();
            tx.commit();

            return users;
        } catch (HibernateException e) {
            if (session.getTransaction() != null) session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            closeSession();
        }

        return null;
    }
}
