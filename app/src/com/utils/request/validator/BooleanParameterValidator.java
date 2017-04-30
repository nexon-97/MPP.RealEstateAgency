package com.utils.request.validator;

import com.services.shared.ServiceManager;

import javax.servlet.http.HttpServletRequest;

public class BooleanParameterValidator  implements RequestParameterValidator<Boolean>, RequestValueContainer<Boolean>  {
    private String paramName;
    private boolean value;
    private String errorMessage;

    public BooleanParameterValidator(String paramName) {
        this.paramName = paramName;
    }

    @Override
    public boolean validate() {
        this.errorMessage = null;
        HttpServletRequest request = ServiceManager.getInstance().getSharedResources().getRequest();
        String paramValue = request.getParameter(this.paramName);
        if(paramValue == "on") {
            this.value = true;
            return true;
        } else if(paramValue == null) {
            this.value = false;
            return true;
        } else {
            errorMessage = "Неправильный формат значения!";
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
    public Boolean getValue() {
        return this.value;
    }
}
