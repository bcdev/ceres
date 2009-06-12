package com.bc.ceres.binding.converters;

import com.bc.ceres.binding.ConversionException;
import com.bc.ceres.binding.Converter;
import com.bc.ceres.java5.StringUtil;

import java.awt.Font;
import java.text.MessageFormat;

public class FontConverter implements Converter<Font> {

    public Class<Font> getValueType() {
        return Font.class;
    }

    public Font parse(String text) throws ConversionException {
        if (StringUtil.isEmpty(text)) {
            return null;
        }

        final String[] tokens;
        try {
            tokens = (String[]) new ArrayConverter(String[].class, new StringConverter()).parse(text);
        } catch (ConversionException e) {
            throw new ConversionException(
                    MessageFormat.format("Cannot parse ''{0}'' into a font: {1}", text, e.getMessage()));
        }
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tokens.length; i++) {
            final String token = tokens[i].trim();
            sb.append(token);
            if (i != tokens.length - 1) {
                sb.append(" ");
            }
        }

        return Font.decode(sb.toString());
    }

    public String format(Font font) {
        if (font == null) {
            return "";
        }

        return String.format("%s,%s,%d", font.getName(), getStyleName(font), font.getSize());
    }

    private static String getStyleName(Font font) {
        final StringBuilder sb = new StringBuilder();

        if (font.isPlain()) {
            sb.append("plain");
        } else {
            if (font.isBold()) {
                sb.append("bold");
            }
            if (font.isItalic()) {
                sb.append("italic");
            }
        }

        return sb.toString();
    }
}
