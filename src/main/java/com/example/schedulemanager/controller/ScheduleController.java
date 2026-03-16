package com.example.schedulemanager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.example.schedulemanager.dto.CreateScheduleRequest;
import com.example.schedulemanager.dto.ScheduleFullResponse;
import com.example.schedulemanager.dto.ScheduleResponse;
import com.example.schedulemanager.service.ScheduleService;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ScheduleResponse create(@RequestBody CreateScheduleRequest request) {
        return scheduleService.create(request);
    }

    @GetMapping("/{id}")
    public ScheduleResponse getById(@PathVariable String id) {
        return scheduleService.getById(id);
    }

    @GetMapping("/full")
    public ScheduleFullResponse getFull(
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String name
    ) {
        return scheduleService.getFull(id, name);
    }
}