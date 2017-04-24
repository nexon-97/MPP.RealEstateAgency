package com.utils.request;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

public class IntegerRangeParameter extends RangeParameter<Integer> {

    public IntegerRangeParameter(int min, int max) {
        super(min, max);
    }

    public static IntegerRangeParameter constructFromStrings(String minStr, String maxStr) {
        return new IntegerRangeParameter(tryParseInteger(minStr), tryParseInteger(maxStr));
    }

    @Override
    public boolean verify() {
        return true;
    }

    @Override
    public void addCriteria(Criteria criteria, String column) {
        criteria.add(Restrictions.between(column, min, max));
    }

    private static int tryParseInteger(String value) {
        try {
            return Integer.valueOf(value);
        } catch (NullPointerException | NumberFormatException e) {
            return 0;
        }
    }
}
