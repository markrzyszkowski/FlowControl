package com.krzyszkowski.sandbox.flowcontrol.module.management.services;

import com.krzyszkowski.sandbox.flowcontrol.module.management.model.Employee;
import com.krzyszkowski.sandbox.flowcontrol.module.management.model.dto.EmployeeDisplayDto;
import com.krzyszkowski.sandbox.flowcontrol.module.management.model.dto.EmployeeDto;
import org.springframework.data.domain.Page;

public interface EmployeeService {

    Employee getEmployee(String username);

    EmployeeDto getEmployee(Long id);

    Page<EmployeeDisplayDto> getPagedEmployees(int page, int size);

    void addEmployee(EmployeeDto employeeDto);

    void updateEmployee(EmployeeDto employeeDto);

    void deleteEmployee(Long id);
}
