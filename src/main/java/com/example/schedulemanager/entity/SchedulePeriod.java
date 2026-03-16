package com.example.schedulemanager.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.example.schedulemanager.enums.SlotType;
import com.example.schedulemanager.util.IdGenerator;

@Entity
@Table(name = "schedule_periods")
@Getter
@Setter
public class SchedulePeriod {

    @Id
    @Column(length = 32, nullable = false)
    private String id;

    @Column(name = "slot_id", nullable = false, length = 32)
    private String slotId;

    @Column(name = "schedule_id", nullable = false, length = 32)
    private String scheduleId;

    @Enumerated(EnumType.STRING)
    @Column(name = "slot_type", nullable = false)
    private SlotType slotType;

    @Column(name = "administrator_id", nullable = false, length = 32)
    private String administratorId;

    @Column(name = "executor_id", length = 32)
    private String executorId;

    @PrePersist
    public void prePersist() {
        if (id == null) {
            id = IdGenerator.newId();
        }
        if (slotType == null) {
            slotType = SlotType.UNDEFINED;
        }
    }
}