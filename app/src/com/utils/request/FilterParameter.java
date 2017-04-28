package com.utils.request;

import org.hibernate.criterion.Criterion;

public interface FilterParameter extends Verifiable {
    PropertyFilterParamId getParamId();
    Criterion getCriterion(String column);
    String getFilterQuery(String entityName, String column);
}
