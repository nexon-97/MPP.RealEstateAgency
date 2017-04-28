package com.dao;

import com.model.Property;
import com.model.User;
import com.utils.request.FilterParameter;
import com.utils.request.PropertyFilterParamId;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PropertyDAOImpl extends BaseDAO implements PropertyDAO {

    @Override
    public Property getPropertyById(int id) {
        Property property = null;

        Session session = openSession();
        if (session != null) {
            try {
                Transaction tx = session.beginTransaction();

                property = (Property)session.get(Property.class, id);

                tx.commit();
            } catch (HibernateException e) {
                e.printStackTrace();
                return null;
            } finally {
                session.close();
            }
        }

        return property;
    }

    @Override
    public boolean updateProperty(Property property) {
        Session session = openSession();
        if (session != null) {
            try {
                Transaction tx = session.beginTransaction();
                session.update(property);
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
    public boolean addProperty(Property property) {
        Session session = openSession();
        if (session != null) {
            try {
                Transaction tx = session.beginTransaction();
                session.save(property);
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
    public boolean deleteProperty(Property property) {
        return false;
    }

    @Override
    public List<Property> getPropertiesOwnedByUser(User user) {
        Session session = openSession();
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
                session.close();
            }
        }

        return null;
    }

    @Override
    public List<Property> list() {
        return null;
    }
}
