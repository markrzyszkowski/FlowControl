package com.krzyszkowski.sandbox.flowcontrol.core.services;

import com.krzyszkowski.sandbox.flowcontrol.core.model.User;
import com.krzyszkowski.sandbox.flowcontrol.core.model.dto.UserDisplayDto;
import com.krzyszkowski.sandbox.flowcontrol.core.model.dto.UserDto;
import com.krzyszkowski.sandbox.flowcontrol.core.model.mapping.UserMapper;
import com.krzyszkowski.sandbox.flowcontrol.core.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Value("${flowcontrol.admin.username}")
    private String adminUsername;
    @Value("${flowcontrol.admin.password}")
    private String adminPassword;

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public Page<UserDisplayDto> getPagedUsers(int page, int size) {
        var sort = Sort.by(List.of(Sort.Order.desc("role"), Sort.Order.asc("username")));
        var pageable = PageRequest.of(page, size, sort);
        return userRepository.findAll(pageable).map(userMapper::toDisplayDto);
    }

    @Override
    @Transactional
    public void addUser(UserDto userDto) {
        userRepository.save(userMapper.fromDto(userDto));
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @PostConstruct
    @Transactional
    public void initAdminCredentials() {
        var user = userRepository.findByUsername(adminUsername);
        if (user.isEmpty()) {
            var admin = new User();
            admin.setUsername(adminUsername);
            admin.setPassword(passwordEncoder.encode(adminPassword));
            admin.setRole(User.Role.ADMIN);
            userRepository.save(admin);
        }
    }
}
