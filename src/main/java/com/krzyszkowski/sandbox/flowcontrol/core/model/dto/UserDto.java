package com.krzyszkowski.sandbox.flowcontrol.core.model.dto;

import com.krzyszkowski.sandbox.flowcontrol.core.model.validation.ValidRole;
import com.krzyszkowski.sandbox.flowcontrol.core.model.validation.ValidUsername;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @NotEmpty(message = "{message.username.empty}")
    @ValidUsername
    private String username;

    @NotEmpty(message = "{message.password.empty}")
    private String password;

    @NotEmpty(message = "{message.role.empty}")
    @ValidRole
    private String role;
}
