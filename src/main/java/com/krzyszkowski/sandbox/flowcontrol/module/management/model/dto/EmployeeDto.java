package com.krzyszkowski.sandbox.flowcontrol.module.management.model.dto;

import com.krzyszkowski.sandbox.flowcontrol.module.management.model.validation.ValidManager;
import com.krzyszkowski.sandbox.flowcontrol.module.management.model.validation.ValidUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {

    private Long id;

    @NotEmpty(message = "{message.name.empty}")
    private String name;

    @NotEmpty(message = "{message.surname.empty}")
    private String surname;

    private String phoneNumber;

    @NotEmpty(message = "{message.street.empty}")
    private String street;

    @NotEmpty(message = "{message.city.empty}")
    private String city;

    @NotEmpty(message = "{message.country.empty}")
    private String country;

    @ValidUser
    private String username;

    @ValidManager
    private String manager;
}
