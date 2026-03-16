package com.example.schedulemanager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.example.schedulemanager.dto.CreateSchedulePeriodRequest;
import com.example.schedulemanager.dto.SchedulePeriodResponse;
import com.example.schedulemanager.exception.BadRequestException;
import com.example.schedulemanager.service.SchedulePeriodService;
import com.example.schedulemanager.dto.SchedulePeriodFilterRequest;

import java.util.List;

@RestController
@RequestMapping("/api/schedule-periods")
@RequiredArgsConstructor
public class SchedulePeriodController {

    private final SchedulePeriodService schedulePeriodService;

    @PostMapping
    public SchedulePeriodResponse create(
            @RequestBody CreateSchedulePeriodRequest request,
            @RequestHeader(value = "x-current-user", required = false) String currentUserId
    ) {
        if (currentUserId == null || currentUserId.isBlank()) {
            throw new BadRequestException("Header x-current-user is required");
        }

        return schedulePeriodService.create(request, currentUserId);
    }

    @GetMapping("/{id}")
    public SchedulePeriodResponse getById(@PathVariable String id) {
        return schedulePeriodService.getById(id);
    }

    @PostMapping("/search")
    public List<SchedulePeriodResponse> search(
            @RequestBody SchedulePeriodFilterRequest filter
    ) {
        return schedulePeriodService.search(filter);
    }

}