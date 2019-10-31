package com.krzyszkowski.sandbox.flowcontrol.module.tasks.model.validation;

import com.krzyszkowski.sandbox.flowcontrol.module.tasks.model.TaskState;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TaskStateValidator implements ConstraintValidator<ValidState, String> {

    @Override
    public void initialize(ValidState constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            TaskState.valueOf(value);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }
}
