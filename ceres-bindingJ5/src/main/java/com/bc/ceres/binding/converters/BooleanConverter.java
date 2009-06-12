package com.bc.ceres.binding.converters;

import com.bc.ceres.binding.ConversionException;
import com.bc.ceres.binding.Converter;
import com.bc.ceres.java5.StringUtil;

public class BooleanConverter implements Converter<Boolean> {
    public Class<Boolean> getValueType() {
        return Boolean.class;
    }

    public Boolean parse(String text) throws ConversionException {
        if (StringUtil.isEmpty(text)) {
            return null;
        }
        return Boolean.parseBoolean(text);
    }

    public String format(Boolean value) {
        if (value == null) {
            return "";
        }
        return value.toString();
    }
}
