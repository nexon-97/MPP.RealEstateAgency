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

    private Map<PropertyFilterParamId, String> propertyColumnMapping;

    public PropertyDAOImpl() {
        propertyColumnMapping = new HashMap<>();
        propertyColumnMapping.put(PropertyFilterParamId.Cost, "cost");
        propertyColumnMapping.put(PropertyFilterParamId.Area, "area");
        propertyColumnMapping.put(PropertyFilterParamId.RoomCount, "roomsCount");
        propertyColumnMapping.put(PropertyFilterParamId.DistanceToSubway, "distanceToSubway");
        propertyColumnMapping.put(PropertyFilterParamId.DistanceToTransportStop, "distanceToTransportStop");
        propertyColumnMapping.put(PropertyFilterParamId.HasFurniture, "hasFurniture");
        propertyColumnMapping.put(PropertyFilterParamId.HasInternet, "hasInternet");
        propertyColumnMapping.put(PropertyFilterParamId.HasTv, "hasTv");
        propertyColumnMapping.put(PropertyFilterParamId.HasPhone, "hasPhone");
        propertyColumnMapping.put(PropertyFilterParamId.HasFridge, "hasFridge");
        propertyColumnMapping.put(PropertyFilterParamId.HasStove, "hasStove");
    }

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
    public List<Property> filter(Map<PropertyFilterParamId, FilterParameter> filterParams) {
        List<Property> filteredProperties = null;

        Session session = openSession();
        if (session != null) {
            try {
                Transaction tx = session.beginTransaction();

                // Construct criteria
                Criteria filterCriteria = session.createCriteria(Property.class);

                for (FilterParameter param : filterParams.values()) {
                    if (param.verify()) {
                        String column = propertyColumnMapping.getOrDefault(param.getParamId(), null);
                        if (column != null) {
                            Criterion criterion = Restrictions.or(Restrictions.isNull(column), param.getCriterion(column));
                            filterCriteria.add(criterion);
                        }
                    }
                }

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
