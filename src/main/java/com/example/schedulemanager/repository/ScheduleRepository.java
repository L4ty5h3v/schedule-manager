package com.example.schedulemanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.schedulemanager.entity.Schedule;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, String> {
    Optional<Schedule> findByScheduleName(String scheduleName);
    List<Schedule> findAll();
}