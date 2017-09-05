package com.dao.impl;

import com.dao.BaseDAO;
import com.dao.PropertyDAO;
import com.model.Property;
import com.model.User;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class PropertyDAOImpl extends BaseDAO implements PropertyDAO {

    @Override
    public Property getPropertyById(int id) {
        Property property = null;

        Session session = getSession();
        if (session != null) {
            try {
                Transaction tx = session.beginTransaction();

                property = (Property)session.get(Property.class, id);

                tx.commit();
            } catch (HibernateException e) {
                e.printStackTrace();
                return null;
            } finally {
                closeSession();
            }
        }

        return property;
    }

    @Override
    public boolean updateProperty(Property property) {
        Session session = getSession();
        if (session != null) {
            try {
                Transaction tx = session.beginTransaction();
                session.update(property);
                tx.commit();
            } catch (HibernateException e) {
                e.printStackTrace();
                return false;
            } finally {
                closeSession();
            }
        }
        return true;
    }

    @Override
    public boolean addProperty(Property property) {
        Session session = getSession();
        if (session != null) {
            try {
                Transaction tx = session.beginTransaction();
                session.save(property);
                tx.commit();
            } catch (HibernateException e) {
                e.printStackTrace();
                return false;
            } finally {
                closeSession();
            }
        }
        return true;
    }

    @Override
    public boolean deleteProperty(Property property) {
        Session session = getSession();
        if (session != null) {
            try {
                Transaction tx = session.beginTransaction();
                session.delete(property);
                tx.commit();

                return true;
            } catch (HibernateException e) {
                e.printStackTrace();
            } finally {
                closeSession();
            }
        }

        return false;
    }

    @Override
    public List<Property> getPropertiesOwnedByUser(User user) {
        Session session = getSession();
        if (session != null) {
            try {
                Transaction tx = session.beginTransaction();

                Criteria filterCriteria = session.createCriteria(Property.class);
                filterCriteria.add(Restrictions.eq("owner", user));
                List<Property> userProperties = (List<Property>)filterCriteria.list();

                tx.commit();

                return userProperties;
            } catch (HibernateException e) {
                e.printStackTrace();
            } finally {
                closeSession();
            }
        }

        return null;
    }

    @Override
    public List<Property> list() {
        Session session = getSession();
        try {
            Transaction tx = session.beginTransaction();
            List<Property> properties = session.createCriteria(Property.class).list();
            tx.commit();
            return properties;
        } catch (HibernateException e) {
            if (session.getTransaction() != null) session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            closeSession();
        }

        return null;
    }
}
