package com.bc.ceres.binding.validators;

import com.bc.ceres.binding.ValidationException;
import com.bc.ceres.binding.Validator;
import com.bc.ceres.binding.ValueModel;

/**
 * This validator delegates validation only if the condition given
 * evaluates to true. This can be used to validate input controls that
 * depend on other input controls or conditions, e.g.:
 * <ul><li>When an x-coordinate is given, y-coordinate must be set too</li>
 * <li>When file input is chosen a given filename must exist</li>
 * </ul> 
 * Recommended use is to implement simple conditions inline, e.g.
 * <code>
 * ValueDescriptur descriptor = ...   ;
 * descriptor.setValidator(new ConditionalValidator(new Condition() {
 *        public boolean eval() {
 *           return valueContainer.getModel("fileOrDatabase").getValueAsText().equals("useFile");
 *        }
 *     }, new FileExistsValidator()));</code>
 * @since 0.9J5, 0.10
 * @author Olaf Kock
 */

public class ConditionalValidator implements Validator {

    private final Condition condition;
    private final Validator delegate;

    /**
     * Condition for ConditionalValidator. Determines, if validation for
     * a given ModelDescriptor should be run. 
     */
    public interface Condition {
        /**
         * Condition under which validation is done.  
         * @return true if validation should be delegated, false if validation should not occur (value is considered valid when returning false)
         */
        boolean eval();
    }

    public ConditionalValidator(Condition condition, Validator delegate) {
        this.condition = condition;
        this.delegate = delegate;
    }

    public void validateValue(ValueModel valueModel, Object value) throws ValidationException {
        if(condition.eval()) {
            delegate.validateValue(valueModel, value);
        }
    }
}
