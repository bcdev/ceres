package com.bc.ceres.binding.converters;

import com.bc.ceres.binding.ConversionException;
import com.bc.ceres.binding.Converter;
import com.bc.ceres.java5.StringUtil;

import java.net.MalformedURLException;
import java.net.URL;

public class UrlConverter implements Converter<URL> {

    public Class<URL> getValueType() {
        return URL.class;
    }

    public URL parse(String text) throws ConversionException {
        if (StringUtil.isEmpty(text)) {
            return null;
        }
        try {
            return new URL(text);
        } catch (MalformedURLException e) {
            throw new ConversionException(e);
        }
    }

    public String format(URL value) {
        if (value == null) {
            return "";
        }
        return value.toExternalForm();
    }
}