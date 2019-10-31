package com.krzyszkowski.sandbox.flowcontrol.module.management.services;

import com.krzyszkowski.sandbox.flowcontrol.core.repositories.UserRepository;
import com.krzyszkowski.sandbox.flowcontrol.module.management.model.Employee;
import com.krzyszkowski.sandbox.flowcontrol.module.management.model.dto.EmployeeDisplayDto;
import com.krzyszkowski.sandbox.flowcontrol.module.management.model.dto.EmployeeDto;
import com.krzyszkowski.sandbox.flowcontrol.module.management.model.mapping.EmployeeMapper;
import com.krzyszkowski.sandbox.flowcontrol.module.management.repositories.EmployeeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    public EmployeeServiceImpl(UserRepository userRepository,
                               EmployeeRepository employeeRepository,
                               EmployeeMapper employeeMapper) {
        this.userRepository = userRepository;
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    @Override
    @Transactional
    public Employee getEmployee(String username) {
        return employeeRepository.findByUserId(userRepository.findByUsername(username)
                                                             .orElseThrow()
                                                             .getId())
                                 .orElseThrow();
    }

    @Override
    @Transactional
    public EmployeeDto getEmployee(Long id) {
        return employeeRepository.findById(id).map(employeeMapper::toDto).orElseThrow();
    }

    @Override
    @Transactional
    public Page<EmployeeDisplayDto> getPagedEmployees(int page, int size) {
        return employeeRepository.findAll(PageRequest.of(page, size, Sort.by(List.of(Sort.Order.asc("surname"),
                                                                                     Sort.Order.asc("name")))))
                                 .map(employeeMapper::toDisplayDto);
    }

    @Override
    @Transactional
    public void addEmployee(EmployeeDto employeeDto) {
        employeeRepository.save(employeeMapper.fromDto(employeeDto));
    }

    @Override
    @Transactional
    public void updateEmployee(EmployeeDto employeeDto) {
        employeeRepository.save(employeeMapper.fromDto(employeeDto));
    }

    @Override
    @Transactional
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
}
