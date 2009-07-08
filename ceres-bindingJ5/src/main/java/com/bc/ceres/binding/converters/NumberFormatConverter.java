package com.bc.ceres.binding.converters;

import com.bc.ceres.binding.ConversionException;
import com.bc.ceres.java5.StringUtil;

import java.text.NumberFormat;

public class NumberFormatConverter implements com.bc.ceres.binding.Converter<Object> {

    private NumberFormat format;
    private Class<? extends Number> numberType;

    public NumberFormatConverter(NumberFormat format) {
        this(format, Number.class);
    }

    public NumberFormatConverter(NumberFormat format, Class<? extends Number> numberType) {
        this.format = format;
        this.numberType = numberType;
    }

    public Class<?> getValueType() {
        return numberType;
    }

    public Object parse(String text) throws ConversionException {
        if (StringUtil.isEmpty(text)) {
            return null;
        }
        try {
            final Number number = format.parse(text);
            if (Double.class.isAssignableFrom(numberType)) {
                if (Double.class.isAssignableFrom(number.getClass())) {
                    return number;
                } else {
                    return number.doubleValue();
                }
            }
            return number;
        } catch (Exception e) {
            throw new ConversionException(e);
        }
    }

    public String format(Object value) {
        return format.format(value);
    }
}
