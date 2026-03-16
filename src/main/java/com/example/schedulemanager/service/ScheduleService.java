package com.example.schedulemanager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.schedulemanager.dto.CreateScheduleRequest;
import com.example.schedulemanager.dto.ScheduleFullResponse;
import com.example.schedulemanager.dto.SchedulePeriodResponse;
import com.example.schedulemanager.dto.ScheduleResponse;
import com.example.schedulemanager.entity.Schedule;
import com.example.schedulemanager.entity.SchedulePeriod;
import com.example.schedulemanager.entity.ScheduleSlot;
import com.example.schedulemanager.exception.BadRequestException;
import com.example.schedulemanager.exception.NotFoundException;
import com.example.schedulemanager.repository.SchedulePeriodRepository;
import com.example.schedulemanager.repository.ScheduleRepository;
import com.example.schedulemanager.repository.ScheduleSlotRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final SchedulePeriodRepository schedulePeriodRepository;
    private final ScheduleSlotRepository scheduleSlotRepository;

    public ScheduleResponse create(CreateScheduleRequest request) {
        Schedule schedule = new Schedule();
        schedule.setScheduleName(request.getScheduleName());

        if (request.getScheduleTags() != null && !request.getScheduleTags().isEmpty()) {
            schedule.setScheduleTags(String.join(",", request.getScheduleTags()));
        }

        Schedule saved = scheduleRepository.save(schedule);
        return toResponse(saved);
    }

    public ScheduleResponse getById(String id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Schedule not found: " + id));

        return toResponse(schedule);
    }

    public ScheduleFullResponse getFull(String id, String name) {
        Schedule schedule;

        if (id != null && !id.isBlank()) {
            schedule = scheduleRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Schedule not found: " + id));
        } else if (name != null && !name.isBlank()) {
            schedule = findByNameOrFuzzy(name);
        } else {
            throw new BadRequestException("Either id or name must be provided");
        }

        List<SchedulePeriod> periods = schedulePeriodRepository.findByScheduleId(schedule.getId());

        List<SchedulePeriodResponse> responses = periods.stream()
                .sorted(Comparator.comparing(period ->
                        scheduleSlotRepository.findById(period.getSlotId())
                                .orElseThrow(() -> new NotFoundException("ScheduleSlot not found: " + period.getSlotId()))
                                .getBeginTime()
                ))
                .map(this::toPeriodResponse)
                .collect(Collectors.toList());

        return ScheduleFullResponse.builder()
                .id(schedule.getId())
                .scheduleName(schedule.getScheduleName())
                .scheduleTags(parseTags(schedule.getScheduleTags()))
                .creationDate(schedule.getCreationDate())
                .periods(responses)
                .build();
    }

    private ScheduleResponse toResponse(Schedule schedule) {
        return ScheduleResponse.builder()
                .id(schedule.getId())
                .scheduleName(schedule.getScheduleName())
                .scheduleTags(parseTags(schedule.getScheduleTags()))
                .creationDate(schedule.getCreationDate())
                .build();
    }

    private SchedulePeriodResponse toPeriodResponse(SchedulePeriod period) {
        return SchedulePeriodResponse.builder()
                .id(period.getId())
                .slotId(period.getSlotId())
                .scheduleId(period.getScheduleId())
                .slotType(period.getSlotType())
                .administratorId(period.getAdministratorId())
                .executorId(period.getExecutorId())
                .build();
    }

    private List<String> parseTags(String tags) {
        if (tags == null || tags.isBlank()) {
            return Collections.emptyList();
        }
        return Arrays.stream(tags.split(","))
                .map(String::trim)
                .collect(Collectors.toList());
    }

    private Schedule findByNameOrFuzzy(String name) {
        return scheduleRepository.findByScheduleName(name)
                .orElseGet(() -> {
                    List<Schedule> allSchedules = scheduleRepository.findAll();

                    Schedule bestMatch = null;
                    int bestDistance = Integer.MAX_VALUE;

                    for (Schedule schedule : allSchedules) {
                        if (schedule.getScheduleName() == null) {
                            continue;
                        }

                        int distance = levenshtein(
                                name.toLowerCase(),
                                schedule.getScheduleName().toLowerCase()
                        );

                        if (distance < bestDistance) {
                            bestDistance = distance;
                            bestMatch = schedule;
                        }
                    }

                    if (bestMatch != null && bestDistance <= 2) {
                        return bestMatch;
                    }

                    throw new NotFoundException("Schedule not found by name: " + name);
                });
    }

    private int levenshtein(String a, String b) {
        int[][] dp = new int[a.length() + 1][b.length() + 1];

        for (int i = 0; i <= a.length(); i++) {
            dp[i][0] = i;
        }

        for (int j = 0; j <= b.length(); j++) {
            dp[0][j] = j;
        }

        for (int i = 1; i <= a.length(); i++) {
            for (int j = 1; j <= b.length(); j++) {
                int cost = a.charAt(i - 1) == b.charAt(j - 1) ? 0 : 1;

                dp[i][j] = Math.min(
                        Math.min(
                                dp[i - 1][j] + 1,
                                dp[i][j - 1] + 1
                        ),
                        dp[i - 1][j - 1] + cost
                );
            }
        }

        return dp[a.length()][b.length()];
    }


}