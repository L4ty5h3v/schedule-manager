package com.example.schedulemanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.example.schedulemanager.entity.SchedulePeriod;

import java.util.List;

public interface SchedulePeriodRepository extends JpaRepository<SchedulePeriod, String>,
        JpaSpecificationExecutor<SchedulePeriod> {

    List<SchedulePeriod> findByScheduleId(String scheduleId);
}