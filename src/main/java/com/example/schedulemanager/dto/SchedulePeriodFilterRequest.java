package com.example.schedulemanager.dto;

import lombok.Getter;
import lombok.Setter;
import com.example.schedulemanager.enums.SlotType;

@Getter
@Setter
public class SchedulePeriodFilterRequest {

    private String id;
    private String slotId;
    private String scheduleId;
    private SlotType slotType;
    private String administratorId;
    private String executorId;

    private Integer page = 0;
    private Integer size = 10;

    private String sort = "id";
}