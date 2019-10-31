package com.krzyszkowski.sandbox.flowcontrol.module.management.model.mapping;

import com.krzyszkowski.sandbox.flowcontrol.core.repositories.UserRepository;
import com.krzyszkowski.sandbox.flowcontrol.module.management.model.Employee;
import com.krzyszkowski.sandbox.flowcontrol.module.management.model.dto.EmployeeDisplayDto;
import com.krzyszkowski.sandbox.flowcontrol.module.management.model.dto.EmployeeDto;
import com.krzyszkowski.sandbox.flowcontrol.module.management.repositories.EmployeeRepository;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapperImpl implements EmployeeMapper {

    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;

    public EmployeeMapperImpl(UserRepository userRepository, EmployeeRepository employeeRepository) {
        this.userRepository = userRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee fromDto(EmployeeDto employeeDto) {
        var employee = employeeDto.getId() != null
                ? employeeRepository.findById(employeeDto.getId()).orElseThrow()
                : new Employee();
        employee.setName(employeeDto.getName());
        employee.setSurname(employeeDto.getSurname());
        employee.setPhoneNumber(employeeDto.getPhoneNumber());
        employee.setStreet(employeeDto.getStreet());
        employee.setCity(employeeDto.getCity());
        employee.setCountry(employeeDto.getCountry());
        employee.setUser(userRepository.findByUsername(employeeDto.getUsername()).orElseThrow());
        employee.setManager(employeeDto.getManager() != null && !employeeDto.getManager().isEmpty()
                                    ? userRepository.findByUsername(employeeDto.getManager()).orElseThrow()
                                    : null);

        return employee;
    }

    @Override
    public EmployeeDto toDto(Employee employee) {
        return new EmployeeDto(employee.getId(),
                               employee.getName(),
                               employee.getSurname(),
                               employee.getPhoneNumber(),
                               employee.getStreet(),
                               employee.getCity(),
                               employee.getCountry(),
                               employee.getUser().getUsername(),
                               employee.getManager() != null ? employee.getManager().getUsername() : null);
    }

    @Override
    public EmployeeDisplayDto toDisplayDto(Employee employee) {
        return new EmployeeDisplayDto(employee.getId(),
                                      employee.getName(),
                                      employee.getSurname(),
                                      employee.getPhoneNumber(),
                                      employee.getStreet(),
                                      employee.getCity(),
                                      employee.getCountry(),
                                      employee.getUser().getUsername(),
                                      employee.getManager() != null ? employee.getManager().getUsername() : null);
    }
}
