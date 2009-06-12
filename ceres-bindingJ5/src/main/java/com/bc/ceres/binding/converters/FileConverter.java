package com.bc.ceres.binding.converters;

import com.bc.ceres.binding.ConversionException;
import com.bc.ceres.binding.Converter;
import com.bc.ceres.java5.StringUtil;

import java.io.File;

public class FileConverter implements Converter<File> {

    public Class<File> getValueType() {
        return File.class;
    }

    public File parse(String text) throws ConversionException {
        if (StringUtil.isEmpty(text)) {
            return null;
        }
        return new File(text);
    }

    public String format(File value) {
        if (value == null) {
            return "";
        }
        return ((File) value).getPath();
    }
}
