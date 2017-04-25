package com.utils.request;

public class IntegerRangeParameter extends RangeParameter<Integer> {

    private class IntegerParseResult {
        public boolean success;
        public int value;

        public IntegerParseResult(int value, boolean success) {
            this.value = value;
            this.success = success;
        }
    }

    public IntegerRangeParameter(String min, String max, PropertyFilterParamId paramId) {
        super(min, max, paramId);
    }

    @Override
    public boolean verify() {
        boolean minValid = tryParseInteger(min).success;
        boolean maxValid = tryParseInteger(max).success;

        return minValid || maxValid;
    }

    @Override
    public Integer getMin() {
        IntegerParseResult parseResult = tryParseInteger(min);
        return parseResult.success ? parseResult.value : Integer.MIN_VALUE;
    }

    @Override
    public Integer getMax() {
        IntegerParseResult parseResult = tryParseInteger(max);
        return parseResult.success ? parseResult.value : Integer.MAX_VALUE;
    }

    private IntegerParseResult tryParseInteger(String value) {
        try {
            int parsedValue = Integer.valueOf(value);
            return new IntegerParseResult(parsedValue, true);
        } catch (NullPointerException | NumberFormatException e) {
            return new IntegerParseResult(0, false);
        }
    }
}
