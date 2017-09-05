package com.utils.request.validator;

import com.utils.request.ParseUtils;

import javax.servlet.http.HttpServletRequest;

public class EnumParameterValidator<T extends Enum<T>> implements RequestParameterValidator<T>, RequestValueContainer<T> {

    private Class<T> enumClass;
    private String paramName;
    private T value;
    private String errorMessage;
    private boolean isNullAllowed;

    public EnumParameterValidator(Class<T> enumClass, String paramName, boolean isNullAllowed) {
        this.enumClass = enumClass;
        this.paramName = paramName;
        this.isNullAllowed = isNullAllowed;
    }

    @Override
    public boolean validate() {
        this.errorMessage = null;
        HttpServletRequest request = null;
        String paramValue = request.getParameter(this.paramName);

        if (paramValue != null) {
            Integer intValue = ParseUtils.parseInteger(paramValue);
            if (intValue != null) {
                if (tryLoadFromInteger(intValue)) {
                    return true;
                }
            } else {
                if (tryLoadFromString(paramValue)) {
                    return true;
                }
            }
        } else if (this.isNullAllowed) {
            return true;
        }

        this.errorMessage = String.format("Не удалось преобразовать параметр '%s' к типу '%s'", this.paramName, this.enumClass.getName());
        return false;
    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public String getParameterName() {
        return paramName;
    }

    private boolean tryLoadFromInteger(int value) {
        try {
            this.value = this.enumClass.getEnumConstants()[value];
            return true;
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    private boolean tryLoadFromString(String value) {
        try {
            this.value = Enum.valueOf(this.enumClass, value);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
