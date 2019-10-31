package com.krzyszkowski.sandbox.flowcontrol.core.model.mapping;

import com.krzyszkowski.sandbox.flowcontrol.core.model.User;
import com.krzyszkowski.sandbox.flowcontrol.core.model.dto.UserDisplayDto;
import com.krzyszkowski.sandbox.flowcontrol.core.model.dto.UserDto;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements UserMapper {

    private final PasswordEncoder passwordEncoder;

    public UserMapperImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User fromDto(UserDto userDto) {
        var user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(User.Role.valueOf(userDto.getRole()));
        return user;
    }

    @Override
    public UserDisplayDto toDisplayDto(User user) {
        return new UserDisplayDto(user.getId(),
                                  user.getUsername(),
                                  user.getRole().name());
    }
}
