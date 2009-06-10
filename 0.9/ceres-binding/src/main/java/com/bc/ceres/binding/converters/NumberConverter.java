package com.bc.ceres.binding.converters;

import com.bc.ceres.binding.ConversionException;
import com.bc.ceres.binding.Converter;
import com.bc.ceres.java5.StringUtil;

public abstract class NumberConverter<T extends Number > implements Converter<T> {

    public abstract Class<? extends T> getValueType() ;

    public T parse(String value) throws ConversionException {
        if (StringUtil.isEmpty(value)) {
            return null;
        }
        try {
            return parseNumber(com.bc.ceres.binding.converters.NumberConverter.trimNumberString(value));
        } catch (NumberFormatException e) {
            throw new ConversionException("'" + value + "' cannot be converted to a number.");
        }
    }

    protected abstract T parseNumber(String value) throws NumberFormatException;

    public String format(T value) {
        if (value == null) {
            return "";
        }
        return value.toString();
    }

    private static String trimNumberString(String s) {
        s = s.trim();
        if (s.startsWith("+")) {
            s = s.substring(1);
        }
        return s;
    }
}
