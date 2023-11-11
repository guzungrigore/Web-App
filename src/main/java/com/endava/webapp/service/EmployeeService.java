package com.endava.webapp.service;

import com.endava.webapp.dto.EmployeeDto;
import com.endava.webapp.model.Employee;
import org.hibernate.service.Service;

import java.util.List;
import java.util.Optional;

public interface EmployeeService{

    public List<EmployeeDto> getAllEmployees();

    public EmployeeDto getEmployeeById(Long id);

    public EmployeeDto saveEmployee(EmployeeDto employeeDto);

    public EmployeeDto updateEmployee(Long id, EmployeeDto updatedEmployee);
}
