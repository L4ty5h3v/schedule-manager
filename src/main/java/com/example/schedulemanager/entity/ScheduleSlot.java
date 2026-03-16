package com.example.schedulemanager.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.example.schedulemanager.enums.Priority;
import com.example.schedulemanager.util.IdGenerator;

import java.time.OffsetTime;

@Entity
@Table(name = "schedule_slots")
@Getter
@Setter
public class ScheduleSlot {

    @Id
    @Column(length = 32, nullable = false)
    private String id;

    @Column(name = "schedule_template_id", nullable = false, length = 32)
    private String scheduleTemplateId;

    @Column(name = "begin_time", nullable = false)
    private OffsetTime beginTime;

    @Column(name = "end_time", nullable = false)
    private OffsetTime endTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false)
    private Priority priority;

    @PrePersist
    public void prePersist() {
        if (id == null) {
            id = IdGenerator.newId();
        }
        if (priority == null) {
            priority = Priority.NORMAL;
        }
    }
}