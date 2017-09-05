package com.utils.request.validator;

public abstract class RegexParameterValidator extends StringParameterValidator {
    public RegexParameterValidator(String paramName, boolean isNullAllowed) {
        super(paramName, isNullAllowed);
    }

    abstract boolean checkRegularExpression(String checkedString);
}
