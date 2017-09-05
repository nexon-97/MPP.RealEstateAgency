package com.utils.request.validator;

import javax.servlet.http.HttpServletRequest;

public class PasswordStringParameterValidator extends StringParameterValidator {
    public PasswordStringParameterValidator(String paramName, boolean isNullAllowed) {
        super(paramName, isNullAllowed);
    }

    @Override
    public boolean validate(HttpServletRequest request) {
        this.errorMessage = null;
        try {
            String paramValue = request.getParameter(this.paramName).trim();
            if ("".equals(paramValue)){
                return checkNullPermission("Пароль отсутствует");
            } else if (! checkDataLength(paramValue, 5, 20) ){
                this.errorMessage = "Длина пароля должна быть 5-20 символов";
                return false;
            } else {
                this.value = paramValue;
                return true;
            }
        }
        catch(NullPointerException e){
            return checkNullPermission("Пароль отсутствует");
        }
    }

    private boolean checkNullPermission(String errorMessage){
        if (isNullAllowed) {
            this.value = null;
            return true;
        }
        this.errorMessage = errorMessage;
        return false;
    }


    private static boolean checkDataLength(String data, int minLength, int maxLength) {
        return (data.length() >= minLength) && (data.length() <= maxLength);
    }
}
