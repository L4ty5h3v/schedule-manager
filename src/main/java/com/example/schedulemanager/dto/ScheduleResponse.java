package com.example.schedulemanager.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Builder
public class ScheduleResponse {
    private String id;
    private String scheduleName;
    private List<String> scheduleTags;
    private OffsetDateTime creationDate;
}