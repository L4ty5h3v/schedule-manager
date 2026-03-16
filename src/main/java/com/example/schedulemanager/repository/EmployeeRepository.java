package com.example.schedulemanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.schedulemanager.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
}