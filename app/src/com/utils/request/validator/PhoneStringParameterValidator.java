package com.utils.request.validator;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneStringParameterValidator extends RegexParameterValidator {

    public PhoneStringParameterValidator(String paramName, boolean isNullAllowed) {
        super(paramName, isNullAllowed);
    }

    @Override
    public boolean validate() {
        this.errorMessage = null;
        try {
            HttpServletRequest request = null;
            String paramValue = request.getParameter(this.paramName).trim();
            if ("".equals(paramValue)){
                return checkNullPermission("Номер телефона отсутствует");
            } else if (! checkRegularExpression(paramValue)){
                this.errorMessage = "Номер телефона должен быт формата +375ххХХХХХХХХ или 80ххХХХХХХХ";
                return false;
            } else {
                this.value = paramValue;
                return true;
            }
        }
        catch(NullPointerException e){
            return checkNullPermission("Номер телефона отсутствует");
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
        Pattern pattern = Pattern.compile("^(\\+375|80)(29|25|44|33)(\\d{3})(\\d{2})(\\d{2})$");
        Matcher matcher = pattern.matcher(checkedString);
        return matcher.find();
    }


}
