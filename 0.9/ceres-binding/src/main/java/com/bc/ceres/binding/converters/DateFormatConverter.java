package com.bc.ceres.binding.converters;

import com.bc.ceres.binding.ConversionException;
import com.bc.ceres.binding.Converter;
import com.bc.ceres.java5.StringUtil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatConverter implements Converter<Date>{
    private DateFormat format;

    public DateFormatConverter() {
        this(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }

    public DateFormatConverter(DateFormat format) {
        this.format = format;
    }

    public Class<Date> getValueType() {
        return Date.class;
    }

    public Date parse(String text) throws ConversionException {
        if (StringUtil.isEmpty(text)) {
            return null;
        }
        try {
            return format.parse(text);
        } catch (ParseException e) {
            throw new ConversionException(e);
        }
    }

    public String format(Date value) {
        if (value == null) {
            return "";
        }
        return format.format( value);
    }
}
