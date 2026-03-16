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
@Table(name = "schedule_templates")
@Getter
@Setter
public class ScheduleTemplate {

    @Id
    @Column(length = 32, nullable = false)
    private String id;

    @Column(name = "creation_date", nullable = false)
    private OffsetDateTime creationDate;

    @Column(name = "template_type", length = 2, nullable = false)
    private String templateType;

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