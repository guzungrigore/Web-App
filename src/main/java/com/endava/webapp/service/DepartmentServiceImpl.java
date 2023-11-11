package com.endava.webapp.service;

import com.endava.webapp.dto.DepartmentDto;
import com.endava.webapp.dto.EmployeeDto;
import com.endava.webapp.exception.DepartmentNotFoundException;
import com.endava.webapp.model.Department;
import com.endava.webapp.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService{

    private final DepartmentRepository departmentRepository;

    @Override
    public List<DepartmentDto> getAllDepartments() {
        return departmentRepository.findAll()
                .stream()
                .map(DepartmentDto::fromDepartment)
                .collect(Collectors.toList());
    }

    @Override
    public DepartmentDto getDepartmentById(Long id) {
        return DepartmentDto.fromDepartment(getDepartment(id));
    }

    @Override
    public DepartmentDto saveDepartment(DepartmentDto departmentDto) {
        return DepartmentDto.fromDepartment(departmentRepository.save(
                Department.builder()
                        .name(departmentDto.getName())
                        .location(departmentDto.getLocation())
                        .build()));
    }

    @Override
    @Transactional
    public DepartmentDto updateDepartment(Long id, DepartmentDto updatedDepartment) {
        Department currentDepartment = getDepartment(id);

        String updatedDepartmentName = updatedDepartment.getName();
        if(areFieldsDifferent(currentDepartment.getName(), updatedDepartmentName)) {
            currentDepartment.setName(updatedDepartmentName);
        }

        String updatedDepartmentLocation = updatedDepartment.getLocation();
        if(areFieldsDifferent(currentDepartment.getLocation(), updatedDepartmentLocation)) {
            currentDepartment.setLocation(updatedDepartmentLocation);
        }

        return DepartmentDto.fromDepartment(currentDepartment);
    }

    private Department getDepartment(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new DepartmentNotFoundException("Department not found"));
    }

    private <T> boolean areFieldsDifferent(T current, T updated) {
        return !Objects.equals(current, updated);
    }
}
