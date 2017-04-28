package com.utils.request;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

public class BooleanParameter implements FilterParameter {

    private PropertyFilterParamId paramId;
    private boolean value;

    public BooleanParameter(PropertyFilterParamId paramId, boolean value) {
        this.paramId = paramId;
        this.value = value;
    }

    @Override
    public boolean verify() {
        return value;
    }

    @Override
    public PropertyFilterParamId getParamId() {
        return paramId;
    }

    @Override
    public Criterion getCriterion(String column) {
        return Restrictions.eq(column, value);
    }

    @Override
    public String getFilterQuery(String entityName, String column) {
        return String.format("(%s.%s = 1)", entityName, column);
    }
}
