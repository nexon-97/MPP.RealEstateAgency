package com.utils.request.filter;

import org.hibernate.Query;

public interface FilterParameter {
    String getFilterQuery(String entityName);
    void fillQuery(Query query);
    String getColumnName();
    boolean isActive();
    Class<?> getParamClass();
}
