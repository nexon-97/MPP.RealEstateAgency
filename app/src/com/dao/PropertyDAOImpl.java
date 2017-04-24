package com.dao;

import com.model.Property;
import com.model.User;
import com.utils.request.FilterParameter;
import com.utils.request.PropertyFilterParam;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

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

    @Override
    public List<Property> filter(Map<PropertyFilterParam, FilterParameter> filterParams) {
        List<Property> filteredProperties = null;

        Session session = openSession();
        if (session != null) {
            try {
                Transaction tx = session.beginTransaction();

                // Construct criteria
                Criteria filterCriteria = session.createCriteria(Property.class);
                filterParams.get(PropertyFilterParam.Area).addCriteria(filterCriteria, "area");

                filteredProperties = filterCriteria.list();

                tx.commit();
            } catch (HibernateException e) {
                e.printStackTrace();
                return null;
            } finally {
                session.close();
            }
        }

        return filteredProperties;
    }
}
