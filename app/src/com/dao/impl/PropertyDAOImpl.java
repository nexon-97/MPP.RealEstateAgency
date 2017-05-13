package com.dao.impl;

import com.dao.AbstractCrudDAO;
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

public class PropertyDAOImpl extends AbstractCrudDAO<Property> implements PropertyDAO {

    public PropertyDAOImpl() {
        super(Property.class);
    }

    @Override
    public List<Property> getPropertiesOwnedByUser(User user) {
        Session session = getSession();
        if (session != null) {
            Criteria criteria = session.createCriteria(Property.class)
                    .add(Restrictions.eq("owner", user));

            return super.filter(criteria);
        }

        return null;
    }
}
