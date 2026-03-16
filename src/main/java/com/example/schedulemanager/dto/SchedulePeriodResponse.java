package com.example.schedulemanager.dto;

import lombok.Builder;
import lombok.Getter;
import com.example.schedulemanager.enums.SlotType;

@Getter
@Builder
public class SchedulePeriodResponse {
    private String id;
    private String slotId;
    private String scheduleId;
    private SlotType slotType;
    private String administratorId;
    private String executorId;
}