package ru.develop.schedule.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public class ProjectPersonId {
    private Long projectId;
    private Long participantId;

    public ProjectPersonId(Long projectId, Long participantId) {
        this.projectId = projectId;
        this.participantId = participantId;
    }

    public ProjectPersonId() {
    }
}
