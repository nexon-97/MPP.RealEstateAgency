package com.utils.request.validator;

import com.services.shared.ServiceManager;

import javax.servlet.http.HttpServletRequest;

public class IntegerParameterValidator implements RequestParameterValidator<Integer>, RequestValueContainer<Integer> {

    private String paramName;
    private Integer value;
    private String errorMessage;
    private boolean isNullAllowed;

    public IntegerParameterValidator(String paramName, boolean isNullAllowed) {
        this.paramName = paramName;
        this.isNullAllowed = isNullAllowed;
    }

    @Override
    public boolean validate() {
        this.errorMessage = null;
        HttpServletRequest request = ServiceManager.getInstance().getSharedResources().getRequest();

        try {
            this.value = Integer.valueOf(request.getParameter(this.paramName));
            if (this.value > 0) return true;
            else {
                this.errorMessage = String.format("Параметр '%s' должен быть больше 0", paramName);
                return false;
            }
        } catch (NullPointerException e) {
            return checkNullPermission(String.format("Параметр '%s' отсутствует", paramName));
        } catch (NumberFormatException e) {
            this.errorMessage = String.format("Параметр '%s' не является числом", paramName);
            return false;
        }
    }

    @Override
    public String getErrorMessage() {
        return this.errorMessage;
    }

    @Override
    public String getParameterName() {
        return this.paramName;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }

    private boolean checkNullPermission(String errorMessage) {
        if (isNullAllowed) {
            this.value = null;
            return true;
        }
        this.errorMessage = errorMessage;
        return false;
    }
}
