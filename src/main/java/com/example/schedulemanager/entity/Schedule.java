package com.example.schedulemanager.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import com.example.schedulemanager.util.IdGenerator;

import java.time.OffsetDateTime;

@Entity
@Table(name = "schedules")
@Getter
@Setter
public class Schedule {

    @Id
    @Column(length = 32, nullable = false)
    private String id;

    @Column(name = "schedule_name")
    private String scheduleName;

    @Column(name = "schedule_tags")
    private String scheduleTags;

    @Column(name = "creation_date", nullable = false)
    private OffsetDateTime creationDate;

    @PrePersist
    public void prePersist() {
        if (id == null) {
            id = IdGenerator.newId();
        }
        if (creationDate == null) {
            creationDate = OffsetDateTime.now();
        }
    }
}