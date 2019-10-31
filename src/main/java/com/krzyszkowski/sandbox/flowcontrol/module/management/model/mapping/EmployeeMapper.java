package com.krzyszkowski.sandbox.flowcontrol.module.management.model.mapping;

import com.krzyszkowski.sandbox.flowcontrol.module.management.model.Employee;
import com.krzyszkowski.sandbox.flowcontrol.module.management.model.dto.EmployeeDisplayDto;
import com.krzyszkowski.sandbox.flowcontrol.module.management.model.dto.EmployeeDto;

public interface EmployeeMapper {

    Employee fromDto(EmployeeDto employeeDto);

    EmployeeDto toDto(Employee employee);

    EmployeeDisplayDto toDisplayDto(Employee employee);
}
