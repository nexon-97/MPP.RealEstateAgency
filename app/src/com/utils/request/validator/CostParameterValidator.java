package com.utils.request.validator;

import com.services.shared.ServiceManager;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CostParameterValidator extends BigDecimalParameterValidator {

    public CostParameterValidator(String paramName, boolean isNullAllowed) {
        super(paramName, isNullAllowed);
    }

    @Override
    public boolean validate() {
        if (super.validate()) {
            String value = ServiceManager.getInstance().getSharedResources().getRequest().getParameter(this.paramName);
            Pattern pattern = Pattern.compile("\\d+(\\.\\d{1,2})?");

            if (value != null && pattern.matcher(value).matches()) {
                if (!(getValue().compareTo(new BigDecimal(0.01)) > 0)) {
                    this.errorMessage = "Нельзя совершать бесплатные сделки!";
                    return false;
                }
            } else {
                this.errorMessage = "Значение не соответствует формату XXX.XX";
                return false;
            }

            return true;
        }

        return false;
    }
}
