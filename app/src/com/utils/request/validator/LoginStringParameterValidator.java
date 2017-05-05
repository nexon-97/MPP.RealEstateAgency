package com.utils.request.validator;

import com.services.shared.ServiceManager;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginStringParameterValidator extends RegexParameterValidator {
    public LoginStringParameterValidator(String paramName, boolean isNullAllowed) {
        super(paramName, isNullAllowed);
    }

    @Override
    public boolean validate() {
        this.errorMessage = null;
        try {
            HttpServletRequest request = ServiceManager.getInstance().getSharedResources().getRequest();
            String paramValue = request.getParameter(this.paramName).trim();
            if ("".equals(paramValue)){
                return checkNullPermission("Логин отсутствует");
            } else if ( !(checkDataLength(paramValue, 5, 20) && checkRegularExpression(paramValue)) ){
                this.errorMessage = "Логин может содержать английские буквы, цифры и символы '_', длина 5-20 символов";
                return false;
            } else {
                this.value = paramValue;
                return true;
            }
        }
        catch(NullPointerException e){
            return checkNullPermission("Логин отсутствует");
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

    @Override
    boolean checkRegularExpression(String checkedString) {
        Pattern pattern = Pattern.compile("[a-zA-Z(\\d)][a-zA-Z(\\d)_]*");
        Matcher matcher = pattern.matcher(checkedString);
        return matcher.find() && matcher.group(0).length() == checkedString.length();
    }

    private static boolean checkDataLength(String data, int minLength, int maxLength) {
        return (data.length() >= minLength) && (data.length() <= maxLength);
    }
}
