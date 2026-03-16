package com.example.schedulemanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.schedulemanager.entity.ScheduleSlot;

public interface ScheduleSlotRepository extends JpaRepository<ScheduleSlot, String> {
}