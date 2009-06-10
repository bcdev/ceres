package com.bc.ceres.binding.converters;

import com.bc.ceres.binding.ConversionException;
import com.bc.ceres.binding.Converter;
import com.bc.ceres.java5.StringUtil;

import java.awt.geom.AffineTransform;
import java.text.MessageFormat;

public class AffineTransformConverter implements Converter<AffineTransform> {

    public Class<? extends AffineTransform> getValueType() {
        return AffineTransform.class;
    }

    public AffineTransform parse(String text) throws ConversionException {
        if (StringUtil.isEmpty(text)) {
            return null;
        }

        final double[] array;
        try {
            array = (double[]) new ArrayConverter(double[].class, new DoubleConverter()).parse(text);
        } catch (ConversionException e) {
            throw new ConversionException(MessageFormat.format(
                    "Cannot parse ''{0}'' into an affine transform: {1}", text, e.getMessage()), e);
        }

        if (array.length != 4 && array.length != 6) {
            throw new ConversionException(MessageFormat.format(
                    "Cannot parse ''{0}'' into an affine transform.", text));
        }

        return new AffineTransform(array);
    }

    public String format(AffineTransform transform) {
        if (transform == null) {
            return "";
        }
        final double[] array = new double[6];
        transform.getMatrix(array);

        return new ArrayConverter(double[].class, new DoubleConverter()).format(array);
    }
}
