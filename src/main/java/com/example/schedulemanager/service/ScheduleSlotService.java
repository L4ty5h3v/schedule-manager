package com.example.schedulemanager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.schedulemanager.dto.CreateScheduleSlotRequest;
import com.example.schedulemanager.dto.ScheduleSlotResponse;
import com.example.schedulemanager.entity.ScheduleSlot;
import com.example.schedulemanager.exception.NotFoundException;
import com.example.schedulemanager.repository.ScheduleSlotRepository;
import com.example.schedulemanager.repository.ScheduleTemplateRepository;

import java.time.OffsetTime;

@Service
@RequiredArgsConstructor
public class ScheduleSlotService {

    private final ScheduleSlotRepository scheduleSlotRepository;
    private final ScheduleTemplateRepository scheduleTemplateRepository;

    public ScheduleSlotResponse create(CreateScheduleSlotRequest request) {
        scheduleTemplateRepository.findById(request.getScheduleTemplateId())
                .orElseThrow(() -> new NotFoundException("ScheduleTemplate not found: " + request.getScheduleTemplateId()));

        ScheduleSlot slot = new ScheduleSlot();
        slot.setScheduleTemplateId(request.getScheduleTemplateId());
        slot.setBeginTime(OffsetTime.parse(request.getBeginTime()));
        slot.setEndTime(OffsetTime.parse(request.getEndTime()));
        slot.setPriority(request.getPriority());

        ScheduleSlot saved = scheduleSlotRepository.save(slot);
        return toResponse(saved);
    }

    public ScheduleSlotResponse getById(String id) {
        ScheduleSlot slot = scheduleSlotRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("ScheduleSlot not found: " + id));

        return toResponse(slot);
    }

    private ScheduleSlotResponse toResponse(ScheduleSlot slot) {
        return ScheduleSlotResponse.builder()
                .id(slot.getId())
                .scheduleTemplateId(slot.getScheduleTemplateId())
                .beginTime(slot.getBeginTime())
                .endTime(slot.getEndTime())
                .priority(slot.getPriority())
                .build();
    }
}