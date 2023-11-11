package com.endava.webapp.dto;

import com.endava.webapp.model.Department;
import com.endava.webapp.model.Employee;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class EmployeeDto {

    private Long id;

    @NotNull(message = "First name should not be null")
    @NotEmpty(message = "First name should not be empty")
    @NotBlank(message = "First name should not be blank")
    private String firstName;

    @NotNull(message = "Last name should not be null")
    @NotEmpty(message = "Last name should not be empty")
    @NotBlank(message = "Last name should not be blank")
    private String lastName;

    @NotNull(message = "Email should not be null")
    @NotEmpty(message = "Email should not be empty")
    @NotBlank(message = "Email should not be blank")
    private String email;

    @NotNull(message = "Phone number should not be null")
    @NotEmpty(message = "Phone number should not be empty")
    @NotBlank(message = "Phone number should not be blank")
    private String phoneNumber;

    @Min(1L)
    private Double salary;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private DepartmentDto departmentDto;

    public static EmployeeDto fromEmployee(Employee employee) {
        return EmployeeDto.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .email(employee.getEmail())
                .phoneNumber(employee.getPhoneNumber())
                .salary(employee.getSalary())
                .departmentDto(Optional.ofNullable(employee.getDepartment()).map(DepartmentDto::fromDepartment).orElse(null))
                .build();
    }
}
