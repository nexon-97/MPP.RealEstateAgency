package com.utils.request;

import org.hibernate.Criteria;

public interface FilterParameter {
    void addCriteria(Criteria criteria, String column);
}
