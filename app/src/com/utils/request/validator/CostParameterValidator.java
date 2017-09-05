package com.utils.request.validator;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.regex.Pattern;

public class CostParameterValidator extends BigDecimalParameterValidator {

    public CostParameterValidator(String paramName, boolean isNullAllowed) {
        super(paramName, isNullAllowed);
    }

    @Override
    public boolean validate() {
        HttpServletRequest request = null;
        String value = request.getParameter(this.paramName);
        Pattern pattern = Pattern.compile("\\d+(\\.\\d{1,2})?");
        boolean match = pattern.matcher(value).matches();

        if (match) {
            if (super.validate()) {
                if (!(getValue().compareTo(new BigDecimal(0.01)) > 0)) {
                    this.errorMessage = "Цена не должна быть меньше 0 $!";
                    return false;
                }

                return true;
            }
        } else {
            this.errorMessage = "Значение не соответствует формату XXX.XX";
        }

        return false;
    }
}
