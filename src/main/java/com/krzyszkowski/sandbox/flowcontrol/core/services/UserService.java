package com.krzyszkowski.sandbox.flowcontrol.core.services;

import com.krzyszkowski.sandbox.flowcontrol.core.model.dto.UserDisplayDto;
import com.krzyszkowski.sandbox.flowcontrol.core.model.dto.UserDto;
import org.springframework.data.domain.Page;

public interface UserService {

    Page<UserDisplayDto> getPagedUsers(int page, int size);

    void addUser(UserDto userDto);

    void deleteUser(Long id);
}
