package com.utils.request.validator;

public interface RequestParameterValidator<T> {
    boolean validate();
    T getValue();
    String getErrorMessage();
    String getParameterName();
}
