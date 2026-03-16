package com.example.schedulemanager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.schedulemanager.dto.CreateScheduleTemplateRequest;
import com.example.schedulemanager.dto.ScheduleTemplateResponse;
import com.example.schedulemanager.entity.ScheduleTemplate;
import com.example.schedulemanager.exception.NotFoundException;
import com.example.schedulemanager.repository.ScheduleTemplateRepository;

@Service
@RequiredArgsConstructor
public class ScheduleTemplateService {

    private final ScheduleTemplateRepository scheduleTemplateRepository;

    public ScheduleTemplateResponse create(CreateScheduleTemplateRequest request) {
        ScheduleTemplate template = new ScheduleTemplate();
        template.setTemplateType(request.getTemplateType());

        ScheduleTemplate saved = scheduleTemplateRepository.save(template);
        return toResponse(saved);
    }

    public ScheduleTemplateResponse getById(String id) {
        ScheduleTemplate template = scheduleTemplateRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("ScheduleTemplate not found: " + id));

        return toResponse(template);
    }

    private ScheduleTemplateResponse toResponse(ScheduleTemplate template) {
        return ScheduleTemplateResponse.builder()
                .id(template.getId())
                .creationDate(template.getCreationDate())
                .templateType(template.getTemplateType())
                .build();
    }
}