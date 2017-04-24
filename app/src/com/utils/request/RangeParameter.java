package com.utils.request;

public abstract class RangeParameter<T> implements Verifiable, FilterParameter {

    protected T min;
    protected T max;

    public RangeParameter(T min, T max) {
        this.min = min;
        this.max = max;
    }

    public void setMin(T min) {
        this.min = min;
    }

    public void setMax(T max) {
        this.max = max;
    }

    public T getMin() {
        return min;
    }

    public T getMax() {
        return max;
    }
}
