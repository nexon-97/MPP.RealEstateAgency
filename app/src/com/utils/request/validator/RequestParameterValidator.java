package com.utils.request.validator;

import javax.servlet.http.HttpServletRequest;

public interface RequestParameterValidator<T> {
    boolean validate(HttpServletRequest request);
    T getValue();
    String getErrorMessage();
    String getParameterName();
}
