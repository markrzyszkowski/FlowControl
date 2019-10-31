package com.krzyszkowski.sandbox.flowcontrol.module.tasks.model.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TaskStateValidator.class)
@Documented
public @interface ValidState {

    String message() default "Invalid task state";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
