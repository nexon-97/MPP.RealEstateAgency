package com.dao.impl;

import com.dao.AbstractCrudDAO;
import com.dao.UserDAO;
import com.model.RoleId;
import com.model.User;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class UserDAOImpl extends AbstractCrudDAO<User> implements UserDAO {

    public UserDAOImpl() {
        super(User.class);
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
            session.close();
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
            session.close();
        }

        return null;
    }

    @Override
    public List<User> getUsersRange(int from, int count) {
        Session session = getSession();
        if (session != null) {
            Criteria filterCriteria = session.createCriteria(User.class)
                    .setFirstResult(from)
                    .setMaxResults(count);

            return super.filter(filterCriteria);
        }

        return null;
    }
}
