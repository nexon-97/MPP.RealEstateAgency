package com.utils.request;

public abstract class ParseUtils {
    public static Integer parseInteger(String strValue) {
        try {
            return Integer.valueOf(strValue);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
