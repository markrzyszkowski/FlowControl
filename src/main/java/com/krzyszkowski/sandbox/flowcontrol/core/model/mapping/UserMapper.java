package com.krzyszkowski.sandbox.flowcontrol.core.model.mapping;

import com.krzyszkowski.sandbox.flowcontrol.core.model.User;
import com.krzyszkowski.sandbox.flowcontrol.core.model.dto.UserDisplayDto;
import com.krzyszkowski.sandbox.flowcontrol.core.model.dto.UserDto;

public interface UserMapper {

    User fromDto(UserDto userDto);

    UserDisplayDto toDisplayDto(User user);
}
