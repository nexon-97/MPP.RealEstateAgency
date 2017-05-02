package com.utils.request.filter;

import org.hibernate.Query;

import java.math.BigDecimal;

public class BigDecimalRangeParameter extends RangeParameter<BigDecimal> {

    private static final BigDecimal infinity = new BigDecimal(1000000);

    private String fieldMinName;
    private String fieldMaxName;

    public BigDecimalRangeParameter(Class<?> paramClass, String paramName, BigDecimal min, BigDecimal max) {
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
        query.setParameter(this.fieldMinName, getMin() != null ? getMin() : BigDecimal.ZERO);
        query.setParameter(this.fieldMaxName, getMax() != null ? getMax() : infinity);
    }
}
