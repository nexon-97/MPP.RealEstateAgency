package com.utils.request.validator;

import com.services.shared.ServiceManager;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

public class BigDecimalParameterValidator implements RequestParameterValidator<BigDecimal>, RequestValueContainer<BigDecimal>  {

    private String paramName;
    private BigDecimal value;
    private String errorMessage;
    private boolean isNullAllowed;

    public BigDecimalParameterValidator(String paramName, boolean isNullAllowed) {
        this.paramName = paramName;
        this.isNullAllowed = isNullAllowed;
    }

    @Override
    public boolean validate() {
        this.errorMessage = null;
        HttpServletRequest request = ServiceManager.getInstance().getSharedResources().getRequest();

        try {
            DecimalFormatSymbols symbols = new DecimalFormatSymbols();
            symbols.setDecimalSeparator('.');
            String pattern = "#0.0#";
            DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
            decimalFormat.setParseBigDecimal(true);

            this.value = (BigDecimal) decimalFormat.parse(request.getParameter(this.paramName));

            return true;
        } catch (ParseException e) {
            this.errorMessage = String.format("Параметр '%s' не является денежным форматом", paramName);
            return false;
        }  catch (NullPointerException e) {
            return checkNullPermission(String.format("Параметр '%s' отсутствует", paramName));
        }
    }

    @Override
    public String getErrorMessage() {
        return this.errorMessage;
    }

    @Override
    public String getParameterName() {
        return this.paramName;
    }

    @Override
    public BigDecimal getValue() {
        return this.value;
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
