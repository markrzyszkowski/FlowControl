package com.krzyszkowski.sandbox.flowcontrol.core.model.dto;

import lombok.Value;

@Value
public class UserDisplayDto {

    private Long id;

    private String username;

    private String role;
}
