package com.bc.ceres.binding.converters;

import com.bc.ceres.binding.Converter;
import com.bc.ceres.binding.ConversionException;
import com.bc.ceres.java5.StringUtil;

public class CharacterConverter implements Converter<Character> {
    public Class<Character> getValueType() {
        return Character.class;
    }

    public Character parse(String text) throws ConversionException {
        if (StringUtil.isEmpty(text)) {
            return null;
        }
        if (text.length() > 1) {
            throw new ConversionException("Not a character: " + text);
        }
        return text.charAt(0);
    }

    public String format(Character value)  {
        if (value == null) {
            return "";
        }
        return value.toString();
    }
}
