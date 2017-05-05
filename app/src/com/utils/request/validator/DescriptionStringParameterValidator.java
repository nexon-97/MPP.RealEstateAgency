package com.utils.request.validator;

import com.services.shared.ServiceManager;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DescriptionStringParameterValidator extends RegexParameterValidator {

    public DescriptionStringParameterValidator(String paramName, boolean isNullAllowed){
        super(paramName, isNullAllowed);
    }

    @Override
    boolean checkRegularExpression(String checkedString) {
        Pattern pattern = Pattern.compile("[a-zA-Zа-яёА-ЯЁ0-9\\.,\\-\\?\\!\\s]+");
        Matcher matcher = pattern.matcher(checkedString);
        return matcher.find() && matcher.group(0).length() == checkedString.length();
    }

    @Override
    public boolean validate() {
        this.errorMessage = null;
        try{
            HttpServletRequest request = ServiceManager.getInstance().getSharedResources().getRequest();
            String paramValue = request.getParameter(this.paramName).trim();
            if("".equals(paramValue)){
                return checkNullPermission(String.format("Параметр '%s' отсутствует", paramName));
            } else if (!checkRegularExpression(paramValue)){
                this.errorMessage = "Описание может содержать русские и английские символы, '.', ',', '?', '!', '-', ' '.";
                return false;
            } else {
                this.value = paramValue;
                return true;
            }
        } catch(NullPointerException e){
            return checkNullPermission(String.format("Параметр '%s' отсутствует", paramName));
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
