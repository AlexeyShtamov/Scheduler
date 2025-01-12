package ru.develop.schedule.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class ProjectPersonId {
    private Long projectId;
    private Long participantId;
}
