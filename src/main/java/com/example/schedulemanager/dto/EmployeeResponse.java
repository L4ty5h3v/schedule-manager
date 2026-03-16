package com.example.schedulemanager.dto;

import lombok.Builder;
import lombok.Getter;
import com.example.schedulemanager.enums.EmployeePosition;
import com.example.schedulemanager.enums.EmployeeStatus;

@Getter
@Builder
public class EmployeeResponse {
    private String id;
    private String employeeName;
    private EmployeeStatus status;
    private EmployeePosition position;
}