package com.endava.webapp.service;

import com.endava.webapp.dto.DepartmentDto;
import com.endava.webapp.model.Department;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public interface DepartmentService {

    public List<DepartmentDto> getAllDepartments();

    public DepartmentDto getDepartmentById(Long id);

    public DepartmentDto saveDepartment(DepartmentDto departmentDto);

    public DepartmentDto updateDepartment(Long id, DepartmentDto updatedDepartment);
}
