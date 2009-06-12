package com.bc.ceres.binding.converters;

import com.bc.ceres.binding.ConversionException;
import com.bc.ceres.binding.Converter;
import com.bc.ceres.java5.StringUtil;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class PatternConverter implements Converter<Pattern> {

    public Class<Pattern> getValueType() {
        return Pattern.class;
    }

    public Pattern parse(String text) throws ConversionException {
        if (StringUtil.isEmpty(text)) {
            return null;
        }
        try {
            return Pattern.compile(text);
        } catch (PatternSyntaxException e) {
            throw new ConversionException(e.getMessage(), e);
        }
    }

    public String format(Pattern value) {
        return value.toString();
    }
}
