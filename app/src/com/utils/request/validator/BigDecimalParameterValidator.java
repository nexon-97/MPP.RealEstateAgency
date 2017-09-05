package com.utils.request.validator;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

public class BigDecimalParameterValidator implements RequestParameterValidator<BigDecimal>, RequestValueContainer<BigDecimal>  {

    protected String paramName;
    protected BigDecimal value;
    protected String errorMessage;
    protected boolean isNullAllowed;

    public BigDecimalParameterValidator(String paramName, boolean isNullAllowed) {
        this.paramName = paramName;
        this.isNullAllowed = isNullAllowed;
    }

    @Override
    public boolean validate() {
        this.errorMessage = null;
        //HttpServletRequest request = ServiceManager.getInstance().getSharedResources().getRequest();
        HttpServletRequest request = null;

        String paramValue = request.getParameter(this.paramName);
        if ((paramValue == null || "".equals(paramValue)) && this.isNullAllowed) {
            return true;
        }

        try {
            DecimalFormatSymbols symbols = new DecimalFormatSymbols();
            symbols.setDecimalSeparator('.');
            String pattern = "0.0";
            DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
            decimalFormat.setParseBigDecimal(true);

            this.value = (BigDecimal) decimalFormat.parse(request.getParameter(this.paramName));

            return true;
        } catch (NullPointerException e) {
            this.errorMessage = String.format("Параметр '%s' отсутствует!", paramName);
            return false;
        } catch (ParseException e) {
            this.errorMessage = String.format("Неверный денежный формат! Введите число в формате XX.XX", paramName);
            return false;
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
}
