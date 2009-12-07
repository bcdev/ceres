package com.bc.ceres.binding.validators;

import com.bc.ceres.binding.ValidationException;
import com.bc.ceres.binding.Validator;
import com.bc.ceres.binding.ValueModel;
import com.bc.ceres.binding.validators.ConditionalValidator.Condition;

import junit.framework.TestCase;

public class TestConditionalValidator extends TestCase {
    private MockCondition falseCondition;
    private MockCondition trueCondition;
    private ValueModel expectedValueModel;
    private Object expectedObject;
    private MockValidator mockValidator;
    
    private class MockCondition implements Condition {
        // count number of calls to make sure that condition 
        // has been evaluated.
        public int numberOfCalls = 0;
        private final boolean result;

        public MockCondition(boolean result) {
            this.result = result;
        }
        
        public boolean eval() {
            numberOfCalls++;
            return result;
        }
    }
    
    private class MockValidator implements Validator {
        // count calls for making sure that the validator has 
        // really been called.
        public int numberOfCalls = 0;
        
        public void validateValue(ValueModel valueModel, Object value) throws ValidationException {
            numberOfCalls++;
            // we know what values this object is tested with - this
            // tests that the actual values are passed to the delegate
            // validator.
            assertSame(expectedValueModel, valueModel);
            assertSame(expectedObject, value);
        }
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        falseCondition = new MockCondition(false);
        trueCondition = new MockCondition(true);
        expectedValueModel = new ValueModel(null, null);
        expectedObject = new Object();
        mockValidator = new MockValidator();
    }
    
    public void testDoesNotDelegateValidationOnFalseCondition() throws Exception {
        ConditionalValidator validator = new ConditionalValidator(falseCondition, mockValidator);
        validator.validateValue(expectedValueModel, expectedObject);
        assertEquals(1, falseCondition.numberOfCalls);
        assertEquals(0, trueCondition.numberOfCalls);
        assertEquals(0, mockValidator.numberOfCalls);
    }
    public void testDelegationOfValidationOnTrueCondition() throws Exception {
        ConditionalValidator validator = new ConditionalValidator(trueCondition, mockValidator);
        validator.validateValue(expectedValueModel, expectedObject);
        assertEquals(0, falseCondition.numberOfCalls);
        assertEquals(1, trueCondition.numberOfCalls);
        assertEquals(1, mockValidator.numberOfCalls);
    }
}
