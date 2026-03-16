package com.example.schedulemanager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.schedulemanager.dto.CreateEmployeeRequest;
import com.example.schedulemanager.dto.EmployeeResponse;
import com.example.schedulemanager.entity.Employee;
import com.example.schedulemanager.exception.NotFoundException;
import com.example.schedulemanager.repository.EmployeeRepository;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeResponse create(CreateEmployeeRequest request) {
        Employee employee = new Employee();
        employee.setEmployeeName(request.getEmployeeName());
        employee.setStatus(request.getStatus());
        employee.setPosition(request.getPosition());

        Employee saved = employeeRepository.save(employee);
        return toResponse(saved);
    }

    public EmployeeResponse getById(String id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Employee not found: " + id));

        return toResponse(employee);
    }

    private EmployeeResponse toResponse(Employee employee) {
        return EmployeeResponse.builder()
                .id(employee.getId())
                .employeeName(employee.getEmployeeName())
                .status(employee.getStatus())
                .position(employee.getPosition())
                .build();
    }
}