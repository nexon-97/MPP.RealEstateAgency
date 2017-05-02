package com.utils.request.filter;

import org.hibernate.Query;

public class IntegerRangeParameter extends RangeParameter<Integer> {

    private String fieldMinName;
    private String fieldMaxName;

    public IntegerRangeParameter(Class<?> paramClass, String paramName, Integer min, Integer max) {
        super(paramClass, paramName, min, max);

        this.fieldMinName = String.format("%s_min", getColumnName());
        this.fieldMaxName = String.format("%s_max", getColumnName());
    }

    @Override
    public String getFilterQuery(String e) {
        return String.format("(%s.%s is null or (%s.%s between :%s and :%s))", e, getColumnName(), e, getColumnName(), this.fieldMinName, this.fieldMaxName);
    }

    @Override
    public void fillQuery(Query query) {
        query.setParameter(this.fieldMinName, getMin() != null ? getMin() : Integer.MIN_VALUE);
        query.setParameter(this.fieldMaxName, getMax() != null ? getMax() : Integer.MAX_VALUE);
    }
}
