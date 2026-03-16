package com.example.schedulemanager.dto;

import lombok.Getter;
import lombok.Setter;
import com.example.schedulemanager.enums.SlotType;

@Getter
@Setter
public class CreateSchedulePeriodRequest {
    private String slotId;
    private String scheduleId;
    private SlotType slotType;
    private String executorId;
}