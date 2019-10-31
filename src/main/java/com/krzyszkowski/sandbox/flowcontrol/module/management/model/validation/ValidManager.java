package com.krzyszkowski.sandbox.flowcontrol.module.management.model.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ManagerValidator.class)
@Documented
public @interface ValidManager {

    String message() default "Selected user is not a manager";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
