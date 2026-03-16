package com.example.schedulemanager.specification;

import org.springframework.data.jpa.domain.Specification;
import com.example.schedulemanager.entity.SchedulePeriod;
import com.example.schedulemanager.enums.SlotType;

public class SchedulePeriodSpecification {

    public static Specification<SchedulePeriod> byFilter(
            String id,
            String slotId,
            String scheduleId,
            SlotType slotType,
            String administratorId,
            String executorId
    ) {

        return (root, query, cb) -> {

            var predicate = cb.conjunction();

            if (id != null) {
                predicate = cb.and(predicate, cb.equal(root.get("id"), id));
            }

            if (slotId != null) {
                predicate = cb.and(predicate, cb.equal(root.get("slotId"), slotId));
            }

            if (scheduleId != null) {
                predicate = cb.and(predicate, cb.equal(root.get("scheduleId"), scheduleId));
            }

            if (slotType != null) {
                predicate = cb.and(predicate, cb.equal(root.get("slotType"), slotType));
            }

            if (administratorId != null) {
                predicate = cb.and(predicate, cb.equal(root.get("administratorId"), administratorId));
            }

            if (executorId != null) {
                predicate = cb.and(predicate, cb.equal(root.get("executorId"), executorId));
            }

            return predicate;
        };
    }
}