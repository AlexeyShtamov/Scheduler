package ru.develop.schedule.domain;

import jakarta.persistence.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.develop.schedule.domain.enums.Role;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectPerson {

    @EmbeddedId
    private ProjectPersonId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("projectId")
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("participantId")
    private Person participant;

    @Enumerated(EnumType.STRING)
    private Role role;
}
