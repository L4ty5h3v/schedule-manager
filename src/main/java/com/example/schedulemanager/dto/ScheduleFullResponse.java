package com.example.schedulemanager.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Builder
public class ScheduleFullResponse {
    private String id;
    private String scheduleName;
    private List<String> scheduleTags;
    private OffsetDateTime creationDate;
    private List<SchedulePeriodResponse> periods;
}