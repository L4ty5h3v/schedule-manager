package com.example.schedulemanager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.example.schedulemanager.dto.CreateScheduleSlotRequest;
import com.example.schedulemanager.dto.ScheduleSlotResponse;
import com.example.schedulemanager.service.ScheduleSlotService;

@RestController
@RequestMapping("/api/schedule-slots")
@RequiredArgsConstructor
public class ScheduleSlotController {

    private final ScheduleSlotService scheduleSlotService;

    @PostMapping
    public ScheduleSlotResponse create(@RequestBody CreateScheduleSlotRequest request) {
        return scheduleSlotService.create(request);
    }

    @GetMapping("/{id}")
    public ScheduleSlotResponse getById(@PathVariable String id) {
        return scheduleSlotService.getById(id);
    }
}