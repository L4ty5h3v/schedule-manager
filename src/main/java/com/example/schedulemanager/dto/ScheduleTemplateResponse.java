package com.example.schedulemanager.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;

@Getter
@Builder
public class ScheduleTemplateResponse {
    private String id;
    private OffsetDateTime creationDate;
    private String templateType;
}