package com.utils.request.validator;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PropertyStringParameterValidator extends RegexParameterValidator {

    public PropertyStringParameterValidator(String paramName, boolean isNullAllowed) {
        super(paramName, isNullAllowed);
    }

    @Override
    public boolean validate() {
        this.errorMessage = null;
        try {
            HttpServletRequest request = null;
            String paramValue = request.getParameter(this.paramName).trim();
            if (Objects.equals(paramValue, "")) {
                return checkNullPermission(String.format("Параметр '%s' отсутствует", paramName));
            } else if (!checkRegularExpression(paramValue)) {
                this.errorMessage = String.format("Параметр '%s' может содержать русские буквы, цифры и символы '-' ''' ' '", paramName);
                return false;
            } else {
                this.value = paramValue;
                return true;
            }
        } catch(NullPointerException e) {
            return checkNullPermission(String.format("Параметр '%s' отсутствует", paramName));
        }
    }

    private boolean checkNullPermission(String errorMessage) {
        if (isNullAllowed) {
            this.value = null;
            return true;
        }

        this.errorMessage = errorMessage;
        return false;
    }

    @Override
    public boolean checkRegularExpression(String value) {
        Pattern pattern = Pattern.compile("^[а-яёА-ЯЁ][а-яёА-ЯЁ\\-'\\s]*$");
        Matcher matcher = pattern.matcher(value);
        return matcher.find();
    }
}
