package com.endava.webapp.service.impl;

import com.endava.webapp.dto.DepartmentDto;
import com.endava.webapp.exception.DepartmentNotFoundException;
import com.endava.webapp.model.Department;
import com.endava.webapp.repository.DepartmentRepository;
import com.endava.webapp.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Override
    public List<DepartmentDto> getAllDepartments() {
        return departmentRepository.findAll().stream()
                .map(DepartmentDto::fromDepartment)
                .collect(Collectors.toList());
    }

    @Override
    public DepartmentDto getDepartmentById(Long id) {
        return DepartmentDto.fromDepartment(getDepartment(id));
    }

    @Override
    @Transactional
    public DepartmentDto saveDepartment(DepartmentDto departmentDto) {
        Department department = Department.builder()
                .name(departmentDto.getName())
                .location(departmentDto.getLocation())
                .build();

        return DepartmentDto.fromDepartment(departmentRepository.save(department));
    }

    @Override
    @Transactional
    public DepartmentDto updateDepartment(Long id, DepartmentDto updatedDepartment) {
        Department currentDepartment = getDepartment(id);

        if(departmentDataChanged(updatedDepartment, DepartmentDto.fromDepartment(currentDepartment))) {
            currentDepartment.setName(updatedDepartment.getName());
            currentDepartment.setLocation(updatedDepartment.getLocation());
        }

        return DepartmentDto.fromDepartment(currentDepartment);
    }

    private static boolean departmentDataChanged(DepartmentDto updatedDepartment, DepartmentDto currentDepartment) {
        currentDepartment.setId(null);
        return !currentDepartment.equals(updatedDepartment);
    }

    private Department getDepartment(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new DepartmentNotFoundException(
                        "Department with id: " + id + " was not found"));
    }
}
