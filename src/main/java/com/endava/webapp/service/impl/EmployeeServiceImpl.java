package com.endava.webapp.service.impl;

import com.endava.webapp.dto.DepartmentDto;
import com.endava.webapp.dto.EmployeeDto;
import com.endava.webapp.exception.DepartmentNotFoundException;
import com.endava.webapp.exception.EmailAlreadyExistsException;
import com.endava.webapp.exception.PhoneNumberAlreadyExistsException;
import com.endava.webapp.exception.UserNotFoundException;
import com.endava.webapp.model.Department;
import com.endava.webapp.model.Employee;
import com.endava.webapp.repository.DepartmentRepository;
import com.endava.webapp.repository.EmployeeRepository;
import com.endava.webapp.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final DepartmentRepository departmentRepository;

    @Override
    public List<EmployeeDto> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(EmployeeDto::fromEmployee)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDto getEmployeeById(Long id) {
        return EmployeeDto.fromEmployee(getEmployee(id));
    }

    @Override
    @Transactional
    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {
        Employee employee = getEmployee(employeeDto);

        return EmployeeDto.fromEmployee(employeeRepository.save(employee));
    }

    @Override
    @Transactional
    public EmployeeDto updateEmployee(Long id, EmployeeDto updatedEmployee) {
        Employee currentEmployee = getEmployee(id);

        if(areEmployeeDiffernt(updatedEmployee, EmployeeDto.fromEmployee(currentEmployee))) {
            updateEmployeeData(updatedEmployee, currentEmployee);
        }

        return EmployeeDto.fromEmployee(currentEmployee);
    }

    private void updateEmployeeData(EmployeeDto updatedEmployee, Employee currentEmployee) {
        String updatedEmployeeEmail = updatedEmployee.getEmail();
        String updatedEmployeePhoneNumber = updatedEmployee.getPhoneNumber();

        checkEmailAvailability(updatedEmployeeEmail);
        checkPhoneNumberAvailability(updatedEmployeePhoneNumber);

        currentEmployee.setFirstName(updatedEmployee.getFirstName());
        currentEmployee.setLastName(updatedEmployee.getLastName());
        currentEmployee.setEmail(updatedEmployeeEmail);
        currentEmployee.setPhoneNumber(updatedEmployeePhoneNumber);
        currentEmployee.setSalary(updatedEmployee.getSalary());
        currentEmployee.setDepartment(getDepartment(updatedEmployee.getDepartmentDto()));
    }

    private Employee getEmployee(EmployeeDto employeeDto) {
        String employeeDtoEmail = employeeDto.getEmail();
        String employeeDtoPhoneNumber = employeeDto.getPhoneNumber();

        checkEmailAvailability(employeeDtoEmail);
        checkPhoneNumberAvailability(employeeDtoPhoneNumber);

        return Employee.builder()
                .firstName(employeeDto.getFirstName())
                .lastName(employeeDto.getLastName())
                .email(employeeDtoEmail)
                .phoneNumber(employeeDtoPhoneNumber)
                .salary(employeeDto.getSalary())
                .department(getDepartment(employeeDto.getDepartmentDto()))
                .build();
    }

    private Department getDepartment(DepartmentDto departmentDto) {
        return Optional.ofNullable(departmentDto)
                .map(department -> departmentRepository.findById(department.getId())
                        .orElseThrow(() -> new DepartmentNotFoundException(
                                "Department with id: " + department.getId() + " was not found")))
                .orElse(null);
    }

    private boolean areEmployeeDiffernt(EmployeeDto updatedEmployee, EmployeeDto currentEmployeeDto) {
        currentEmployeeDto.setId(null);
        return !currentEmployeeDto.equals(updatedEmployee);
    }

    private Employee getEmployee(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Employee with id: " + id + " was not found"));
    }

    private void checkEmailAvailability(String email) {
        if(employeeRepository.findByEmail(email).isPresent()) {
            throw new EmailAlreadyExistsException("Email: " + email + " already exists");
        }
    }

    private void checkPhoneNumberAvailability(String phoneNumber) {
        if(employeeRepository.findByPhoneNumber(phoneNumber).isPresent()) {
            throw new PhoneNumberAlreadyExistsException("Phone number: " + phoneNumber + " already exists");
        }
    }
}