package com.example.schedulemanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.schedulemanager.entity.ScheduleTemplate;

public interface ScheduleTemplateRepository extends JpaRepository<ScheduleTemplate, String> {
}