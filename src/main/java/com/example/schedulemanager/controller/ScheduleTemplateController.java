package com.example.schedulemanager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.example.schedulemanager.dto.CreateScheduleTemplateRequest;
import com.example.schedulemanager.dto.ScheduleTemplateResponse;
import com.example.schedulemanager.service.ScheduleTemplateService;

@RestController
@RequestMapping("/api/schedule-templates")
@RequiredArgsConstructor
public class ScheduleTemplateController {

    private final ScheduleTemplateService scheduleTemplateService;

    @PostMapping
    public ScheduleTemplateResponse create(@RequestBody CreateScheduleTemplateRequest request) {
        return scheduleTemplateService.create(request);
    }

    @GetMapping("/{id}")
    public ScheduleTemplateResponse getById(@PathVariable String id) {
        return scheduleTemplateService.getById(id);
    }
}