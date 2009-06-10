package com.bc.ceres.binding.validators;

import com.bc.ceres.binding.ValidationException;
import com.bc.ceres.binding.Validator;
import com.bc.ceres.binding.ValueModel;
import com.bc.ceres.java5.StringUtil;

import java.text.MessageFormat;

public class NotEmptyValidator implements Validator {
    public void validateValue(ValueModel valueModel, Object value) throws ValidationException {
        if (value == null || StringUtil.isEmpty(value.toString().trim())) {
            throw new ValidationException(MessageFormat.format("No value for ''{0}'' specified.", 
                                                               valueModel.getDescriptor().getDisplayName()));
        }
    }
}
