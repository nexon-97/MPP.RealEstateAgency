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

    public BigDecimalParameterValidator(String paramName) {
        this.paramName = paramName;
    }

    @Override
    public boolean validate() {
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
        }  catch (NullPointerException e) {
            this.errorMessage = String.format("Параметр '%s' отстуствует", paramName);
        }

        return false;
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
