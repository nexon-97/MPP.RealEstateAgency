package com.utils.request.validator;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailStringParameterValidator extends RegexParameterValidator {

    public EmailStringParameterValidator(String paramName, boolean isNullAllowed) {
        super(paramName, isNullAllowed);
    }

    @Override
    boolean checkRegularExpression(String checkedString) {
        Pattern pattern = Pattern.compile(".+@.+\\..+");
        Matcher matcher = pattern.matcher(checkedString);
        return matcher.find() && matcher.group(0).length() == checkedString.length();
    }

    @Override
    public boolean validate() {
        this.errorMessage = null;
        try {
            HttpServletRequest request = null;
            String paramValue = request.getParameter(this.paramName).trim();
            if ("".equals(paramValue)){
                return checkNullPermission("E-mail отсутствует");
            } else if (! checkRegularExpression(paramValue)){
                this.errorMessage = "E-mail должен быть существующим";
                return false;
            } else {
                this.value = paramValue;
                return true;
            }
        }
        catch(NullPointerException e){
            return checkNullPermission("E-mail отсутствует");
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
}
