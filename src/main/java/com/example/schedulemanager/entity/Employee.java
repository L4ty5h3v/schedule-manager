package com.example.schedulemanager.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.example.schedulemanager.enums.EmployeePosition;
import com.example.schedulemanager.enums.EmployeeStatus;
import com.example.schedulemanager.util.IdGenerator;

@Entity
@Table(name = "employees")
@Getter
@Setter
public class Employee {

    @Id
    @Column(length = 32, nullable = false)
    private String id;

    @Column(name = "employee_name", nullable = false)
    private String employeeName;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private EmployeeStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "position", nullable = false)
    private EmployeePosition position;

    @PrePersist
    public void prePersist() {
        if (id == null) {
            id = IdGenerator.newId();
        }
        if (position == null) {
            position = EmployeePosition.UNDEFINED;
        }
    }
}