package com.utils.request.validator;

import com.services.shared.ServiceManager;

import javax.servlet.http.HttpServletRequest;

public class IntegerParameterValidator implements RequestParameterValidator<Integer>, RequestValueContainer<Integer> {

    private String paramName;
    private int value;
    private String errorMessage;

    public IntegerParameterValidator(String paramName) {
        this.paramName = paramName;
    }

    @Override
    public boolean validate() {
        try {
            HttpServletRequest request = ServiceManager.getInstance().getSharedResources().getRequest();
            this.value = Integer.valueOf(request.getParameter(this.paramName));

            return true;
        } catch (NullPointerException e) {
            this.errorMessage = String.format("Параметр '%s' отсутствует в запросе", paramName);
        } catch (NumberFormatException e) {
            this.errorMessage = String.format("Параметр '%s' не является числом", paramName);
        }

        return false;
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
}
