package com.utils.request.validator;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FullNameStringParameterValidator extends RegexParameterValidator{

    public FullNameStringParameterValidator(String paramName, boolean isNullAllowed) {
        super(paramName, isNullAllowed);
    }

    @Override
    public boolean validate() {
        this.errorMessage = null;
        try {
            HttpServletRequest request = null;
            String paramValue = request.getParameter(this.paramName).trim();
            String paramRusName;
            if (paramName.equals("name")){
                paramRusName = "Имя";
            } else if (paramName.equals("surname")){
                paramRusName = "Фамилия";
            } else {
                paramRusName = "Отчество";
            }
            if ("".equals(paramValue)){
                return checkNullPermission(String.format("%s отсутствует", paramRusName));
            } else if (!checkRegularExpression(paramValue)){
                this.errorMessage = String.format("%s может содержать русские буквы, пробелы и символы '-', длина 3-40 символов", paramRusName);
                return false;
            } else {
                this.value = paramValue;
                return true;
            }
        }
        catch(NullPointerException e){
            return checkNullPermission("Параметр отсутствует");
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
        Pattern pattern = Pattern.compile("[a-zA-Zа-яёА-ЯЁ][a-zA-Zа-яёА-ЯЁ\\-'\\s]*");
        Matcher matcher = pattern.matcher(checkedString);
        return matcher.find() && matcher.group(0).length() == checkedString.length();
    }

}
