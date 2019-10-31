package com.krzyszkowski.sandbox.flowcontrol.module.management.model.dto;

import lombok.Value;

@Value
public class EmployeeDisplayDto {

    private Long id;

    private String name;

    private String surname;

    private String phoneNumber;

    private String street;

    private String city;

    private String country;

    private String username;

    private String manager;
}
