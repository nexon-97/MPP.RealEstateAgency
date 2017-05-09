package com.utils.request.validator;

import java.math.BigDecimal;

public class CostParameterValidator extends BigDecimalParameterValidator {

    public CostParameterValidator(String paramName, boolean isNullAllowed) {
        super(paramName, isNullAllowed);
    }

    @Override
    public boolean validate() {
        if (super.validate()) {
            boolean valid = this.getValue().compareTo(new BigDecimal(0.001)) > 0;

            if (!valid) {
                this.errorMessage = "Цена не должна быть меньше 0 $!";
            }

            return valid;
        }

        return false;
    }
}
