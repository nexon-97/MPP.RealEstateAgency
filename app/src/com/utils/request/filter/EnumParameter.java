package com.utils.request.filter;

import com.model.Offer;
import org.hibernate.Query;

public class EnumParameter <T extends Enum<T>> implements FilterParameter {

    private T value;
    private String column;

    public EnumParameter(String column, T value) {
        this.column = column;
        this.value = value;
    }

    @Override
    public String getFilterQuery(String entityName) {
        return String.format("(%s.%s = %d)", entityName, getColumnName(), this.value.ordinal());
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
        return this.value != null;
    }

    @Override
    public Class<?> getParamClass() {
        return Offer.class;
    }
}
