package com.example.schedulemanager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.schedulemanager.dto.CreateSchedulePeriodRequest;
import com.example.schedulemanager.dto.SchedulePeriodResponse;
import com.example.schedulemanager.entity.Schedule;
import com.example.schedulemanager.entity.SchedulePeriod;
import com.example.schedulemanager.entity.ScheduleSlot;
import com.example.schedulemanager.exception.ConflictException;
import com.example.schedulemanager.exception.NotFoundException;
import com.example.schedulemanager.repository.EmployeeRepository;
import com.example.schedulemanager.repository.SchedulePeriodRepository;
import com.example.schedulemanager.repository.ScheduleRepository;
import com.example.schedulemanager.repository.ScheduleSlotRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import com.example.schedulemanager.dto.SchedulePeriodFilterRequest;
import com.example.schedulemanager.specification.SchedulePeriodSpecification;

import java.util.stream.Collectors;
import java.util.List;


@Service
@RequiredArgsConstructor
public class SchedulePeriodService {

    private final SchedulePeriodRepository schedulePeriodRepository;
    private final ScheduleSlotRepository scheduleSlotRepository;
    private final ScheduleRepository scheduleRepository;
    private final EmployeeRepository employeeRepository;

    public SchedulePeriodResponse create(CreateSchedulePeriodRequest request, String currentUserId) {
        ScheduleSlot newSlot = scheduleSlotRepository.findById(request.getSlotId())
                .orElseThrow(() -> new NotFoundException("ScheduleSlot not found: " + request.getSlotId()));

        Schedule schedule = scheduleRepository.findById(request.getScheduleId())
                .orElseThrow(() -> new NotFoundException("Schedule not found: " + request.getScheduleId()));

        if (request.getExecutorId() != null) {
            employeeRepository.findById(request.getExecutorId())
                    .orElseThrow(() -> new NotFoundException("Employee not found: " + request.getExecutorId()));
        }

        validateNoOverlap(schedule.getId(), newSlot);

        SchedulePeriod period = new SchedulePeriod();
        period.setSlotId(request.getSlotId());
        period.setScheduleId(request.getScheduleId());
        period.setSlotType(request.getSlotType());
        period.setAdministratorId(currentUserId);

        if (request.getExecutorId() != null && !request.getExecutorId().equals(currentUserId)) {
            period.setExecutorId(request.getExecutorId());
        } else {
            period.setExecutorId(null);
        }

        SchedulePeriod saved = schedulePeriodRepository.save(period);
        return toResponse(saved);
    }

    public SchedulePeriodResponse getById(String id) {
        SchedulePeriod period = schedulePeriodRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("SchedulePeriod not found: " + id));

        return toResponse(period);
    }

    private void validateNoOverlap(String scheduleId, ScheduleSlot newSlot) {
        List<SchedulePeriod> existingPeriods = schedulePeriodRepository.findByScheduleId(scheduleId);

        for (SchedulePeriod existingPeriod : existingPeriods) {
            ScheduleSlot existingSlot = scheduleSlotRepository.findById(existingPeriod.getSlotId())
                    .orElseThrow(() -> new NotFoundException("ScheduleSlot not found: " + existingPeriod.getSlotId()));

            boolean overlaps =
                    newSlot.getBeginTime().isBefore(existingSlot.getEndTime()) &&
                            newSlot.getEndTime().isAfter(existingSlot.getBeginTime());

            if (overlaps) {
                throw new ConflictException(
                        "Schedule period overlaps with existing period: " + existingPeriod.getId()
                );
            }
        }
    }

    private SchedulePeriodResponse toResponse(SchedulePeriod period) {
        return SchedulePeriodResponse.builder()
                .id(period.getId())
                .slotId(period.getSlotId())
                .scheduleId(period.getScheduleId())
                .slotType(period.getSlotType())
                .administratorId(period.getAdministratorId())
                .executorId(period.getExecutorId())
                .build();
    }

    public List<SchedulePeriodResponse> search(SchedulePeriodFilterRequest filter) {

        Sort sort = Sort.by(filter.getSort());

        PageRequest pageRequest = PageRequest.of(
                filter.getPage(),
                filter.getSize(),
                sort
        );

        var spec = SchedulePeriodSpecification.byFilter(
                filter.getId(),
                filter.getSlotId(),
                filter.getScheduleId(),
                filter.getSlotType(),
                filter.getAdministratorId(),
                filter.getExecutorId()
        );

        Page<SchedulePeriod> page = schedulePeriodRepository.findAll(spec, pageRequest);

        return page.getContent()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}