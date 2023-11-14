package com.endava.webapp.service;

import com.endava.webapp.dto.EmployeeDto;

import java.util.List;

public interface EmployeeService{

    List<EmployeeDto> getAllEmployees();

    EmployeeDto getEmployeeById(Long id);

    EmployeeDto saveEmployee(EmployeeDto employeeDto);

    EmployeeDto updateEmployee(Long id, EmployeeDto updatedEmployee);
}
