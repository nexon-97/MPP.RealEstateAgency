package com.converter;

import com.model.PropertyType;
import org.springframework.core.convert.converter.Converter;

public class PropertyTypeConverter implements Converter<String, PropertyType> {

    public PropertyType convert(String source) {
        try {
            return PropertyType.valueOf(source);
        } catch (Exception ex) {
            return PropertyType.Flat;
        }
    }

}
