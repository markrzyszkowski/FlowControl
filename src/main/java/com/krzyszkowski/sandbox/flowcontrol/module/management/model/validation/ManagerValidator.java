package com.krzyszkowski.sandbox.flowcontrol.module.management.model.validation;

import com.krzyszkowski.sandbox.flowcontrol.core.model.User;
import com.krzyszkowski.sandbox.flowcontrol.core.repositories.UserRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ManagerValidator implements ConstraintValidator<ValidManager, String> {

    private final UserRepository userRepository;

    public ManagerValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void initialize(ValidManager constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null
               || value.isEmpty()
               || userRepository.findByUsername(value).filter(user -> user.getRole() == User.Role.MANAGER).isPresent();
    }
}
