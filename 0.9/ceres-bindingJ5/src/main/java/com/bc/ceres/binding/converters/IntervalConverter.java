package com.bc.ceres.binding.converters;

import com.bc.ceres.binding.ConversionException;
import com.bc.ceres.binding.ValueRange;
import com.bc.ceres.java5.StringUtil;

public class IntervalConverter implements com.bc.ceres.binding.Converter<ValueRange> {
    public Class<ValueRange> getValueType() {
        return ValueRange.class;
    }

    public ValueRange parse(String text) throws ConversionException {
        if (StringUtil.isEmpty(text)) {
            return null;
        }
        return ValueRange.parseValueRange(text);
    }

    public String format(ValueRange value) {
        if (value == null) {
            return "";
        }
        return value.toString();
    }
}
