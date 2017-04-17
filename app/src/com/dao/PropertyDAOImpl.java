package com.dao;

import com.model.Property;
import com.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

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
        return false;
    }

    @Override
    public boolean deleteProperty(Property property) {
        return false;
    }

    @Override
    public List<Property> getPropertiesOwnedByUser(User user) {
        return null;
    }

    @Override
    public List<Property> list() {
        return null;
    }
}
