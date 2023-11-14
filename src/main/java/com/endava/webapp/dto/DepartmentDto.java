package com.endava.webapp.dto;

import com.endava.webapp.model.Department;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DepartmentDto {

    private Long id;

    @NotNull(message = "Department name should not be null")
    @NotEmpty(message = "Department name should not be empty")
    @NotBlank(message = "Department name should not be blank")
    private String name;

    @NotNull(message = "Location should not be null")
    @NotEmpty(message = "Location should not be empty")
    @NotBlank(message = "Location should not be blank")
    private String location;

    public static DepartmentDto fromDepartment(Department department) {
        return DepartmentDto.builder()
                .id(department.getId())
                .name(department.getName())
                .location(department.getLocation())
                .build();
    }
}
