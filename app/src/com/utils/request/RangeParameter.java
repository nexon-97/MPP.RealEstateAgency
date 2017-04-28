package com.utils.request;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

public abstract class RangeParameter<T> implements Verifiable, FilterParameter {

    private PropertyFilterParamId paramId;
    protected String min;
    protected String max;

    public RangeParameter(String min, String max, PropertyFilterParamId paramId) {
        this.min = min;
        this.max = max;
        this.paramId = paramId;
    }

    abstract public T getMin();

    abstract public T getMax();

    @Override
    public PropertyFilterParamId getParamId() {
        return paramId;
    }

    @Override
    public Criterion getCriterion(String column) {
        return Restrictions.between(column, getMin(), getMax());
    }

    @Override
    public String getFilterQuery(String e, String column) {
        return String.format("(%s.%s is null or (%s.%s between %s and %s))", e, column, e, column, String.valueOf(getMin()), String.valueOf(getMax()));
    }
}
