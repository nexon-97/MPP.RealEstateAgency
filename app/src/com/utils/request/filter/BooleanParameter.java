package com.utils.request.filter;

import org.hibernate.Query;

public class BooleanParameter implements FilterParameter {

    private boolean value;
    private String column;
    private Class<?> paramClass;

    public BooleanParameter(Class<?> paramClass, String column, Boolean value) {
        this.paramClass = paramClass;
        this.column = column;
        this.value = value;
    }

    @Override
    public String getFilterQuery(String entityName) {
        return String.format("(%s.%s = 1)", entityName, getColumnName());
    }

    @Override
    public void fillQuery(Query query) {

    }

    @Override
    public String getColumnName() {
        return this.column;
    }

    @Override
    public boolean isActive() {
        return this.value;
    }

    @Override
    public Class<?> getParamClass() {
        return this.paramClass;
    }
}
