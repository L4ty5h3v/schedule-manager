package com.example.schedulemanager.dto;

import lombok.Getter;
import lombok.Setter;
import com.example.schedulemanager.enums.Priority;

@Getter
@Setter
public class CreateScheduleSlotRequest {
    private String scheduleTemplateId;
    private String beginTime;
    private String endTime;
    private Priority priority;
}