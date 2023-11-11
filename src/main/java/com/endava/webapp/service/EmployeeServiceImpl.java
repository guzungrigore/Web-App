package com.endava.webapp.service;

import com.endava.webapp.dto.DepartmentDto;
import com.endava.webapp.dto.EmployeeDto;
import com.endava.webapp.exception.PhoneNumberAlreadyExistsException;
import com.endava.webapp.exception.UserNotFoundException;
import com.endava.webapp.model.Department;
import com.endava.webapp.model.Employee;
import com.endava.webapp.repository.DepartmentRepository;
import com.endava.webapp.repository.EmployeeRepository;
import com.endava.webapp.exception.EmailAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService{

    private final EmployeeRepository employeeRepository;

    private final DepartmentRepository departmentRepository;

    @Override
    public List<EmployeeDto> getAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(EmployeeDto::fromEmployee)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDto getEmployeeById(Long id) {
        return EmployeeDto.fromEmployee(getEmployee(id));
    }

    @Override
    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {
        String employeeDtoEmail = employeeDto.getEmail();
        String employeeDtoPhoneNumber = employeeDto.getPhoneNumber();

        doesEmailExist(employeeDtoEmail);
        doesPhoneNumberExist(employeeDtoPhoneNumber);

        return EmployeeDto.fromEmployee(employeeRepository.save(
                    Employee.builder()
                            .firstName(employeeDto.getFirstName())
                            .lastName(employeeDto.getLastName())
                            .email(employeeDtoEmail)
                            .phoneNumber(employeeDtoPhoneNumber)
                            .salary(employeeDto.getSalary())
                            .department(getDepartment(employeeDto.getDepartmentDto()).orElse(null))
                            .build()));
    }
    private Optional<Department> getDepartment(DepartmentDto departmentDto) {
        if (Objects.isNull(departmentDto)) return Optional.empty();

        Optional<Department> department = departmentRepository.findById(departmentDto.getId());
        if(department.isEmpty()) {
            throw new IllegalArgumentException();
        } else
            return department;
    }

    @Override
    @Transactional
    public EmployeeDto updateEmployee(Long id, EmployeeDto updatedEmployee) {
        Employee currentEmployee = getEmployee(id);

        String updatedEmployeeFirstName = updatedEmployee.getFirstName();
        if(areFieldsDifferent(currentEmployee.getFirstName(), updatedEmployeeFirstName)) {
            currentEmployee.setFirstName(updatedEmployeeFirstName);
        }

        String updatedEmployeeLastName = updatedEmployee.getLastName();
        if (areFieldsDifferent(currentEmployee.getLastName(), updatedEmployeeLastName)) {
            currentEmployee.setLastName(updatedEmployeeLastName);
        }

        String updatedEmployeeEmail = updatedEmployee.getEmail();
        if (areFieldsDifferent(currentEmployee.getEmail(), updatedEmployeeEmail)) {
            doesEmailExist(updatedEmployeeEmail);
            currentEmployee.setEmail(updatedEmployeeEmail);
        }

        String updatedEmployeePhoneNumber = updatedEmployee.getPhoneNumber();
        if (areFieldsDifferent(currentEmployee.getPhoneNumber(), updatedEmployeePhoneNumber)) {
            doesPhoneNumberExist(updatedEmployeePhoneNumber);
            currentEmployee.setPhoneNumber(updatedEmployeePhoneNumber);
        }

        Double updatedEmployeeSalary = updatedEmployee.getSalary();
        if(areFieldsDifferent(currentEmployee.getSalary(), updatedEmployeeSalary)) {
            currentEmployee.setSalary(updatedEmployeeSalary);
        }

        Optional<Department> updatedDepartment = getDepartment(updatedEmployee.getDepartmentDto());
        if (areFieldsDifferent(currentEmployee.getDepartment(), updatedDepartment)) {
            currentEmployee.setDepartment(updatedDepartment.orElse(null));
        }

        return EmployeeDto.fromEmployee(currentEmployee);
    }

    private Employee getEmployee(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Employee not found"));
    }

    private void doesEmailExist(String email) {
        if(employeeRepository.findByEmail(email).isPresent()) {
            throw new EmailAlreadyExistsException("Email: " + email + " already exists");
        }
    }

    private void doesPhoneNumberExist(String phoneNumber) {
        if(employeeRepository.findByPhoneNumber(phoneNumber).isPresent()) {
            throw new PhoneNumberAlreadyExistsException("Phone number: " + phoneNumber + " already exists");
        }
    }

    private <T> boolean areFieldsDifferent(T current, T updated) {
        return !Objects.equals(current, updated);
    }
}