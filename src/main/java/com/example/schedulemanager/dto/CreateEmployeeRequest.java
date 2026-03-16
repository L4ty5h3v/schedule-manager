package com.example.schedulemanager.dto;

import lombok.Getter;
import lombok.Setter;
import com.example.schedulemanager.enums.EmployeePosition;
import com.example.schedulemanager.enums.EmployeeStatus;

@Getter
@Setter
public class CreateEmployeeRequest {
    private String employeeName;
    private EmployeeStatus status;
    private EmployeePosition position;
}