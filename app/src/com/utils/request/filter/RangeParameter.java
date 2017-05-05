package com.utils.request.filter;

public abstract class RangeParameter<T> implements FilterParameter {

    protected T min;
    protected T max;
    protected String column;
    protected Class<?> paramClass;

    public RangeParameter(Class<?> paramClass, String column, T min, T max) {
        this.paramClass = paramClass;
        this.column = column;
        this.min = min;
        this.max = max;
    }

    public T getMin() {
        return this.min;
    }

    public T getMax() {
        return this.max;
    }

    @Override
    public String getColumnName() {
        return this.column;
    }

    @Override
    public boolean isActive() {
        return this.min != null || this.max != null;
    }

    @Override
    public Class<?> getParamClass() {
        return this.paramClass;
    }
}
