package com.example.schedulemanager.dto;

import lombok.Builder;
import lombok.Getter;
import com.example.schedulemanager.enums.Priority;

import java.time.OffsetTime;

@Getter
@Builder
public class ScheduleSlotResponse {
    private String id;
    private String scheduleTemplateId;
    private OffsetTime beginTime;
    private OffsetTime endTime;
    private Priority priority;
}