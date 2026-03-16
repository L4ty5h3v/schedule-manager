package com.example.schedulemanager.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateScheduleRequest {
    private String scheduleName;
    private List<String> scheduleTags;
}