package com.utils.request.validator;

import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;

public class StringParameterValidator implements RequestParameterValidator<String>, RequestValueContainer<String>  {
    protected String paramName;
    protected String value;
    protected String errorMessage;
    protected boolean isNullAllowed;

    public StringParameterValidator(String paramName, boolean isNullAllowed) {
        this.paramName = paramName;
        this.isNullAllowed = isNullAllowed;
    }

    @Override
    public boolean validate() {
        //HttpServletRequest request = ServiceManager.getInstance().getSharedResources().getRequest();
        HttpServletRequest request = null;
                this.value = request.getParameter(this.paramName);

        return this.value != null;
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
    public String getValue() {
        if (this.value != null) {
            return HtmlUtils.htmlEscape(this.value);
        }

        return null;
    }
}
