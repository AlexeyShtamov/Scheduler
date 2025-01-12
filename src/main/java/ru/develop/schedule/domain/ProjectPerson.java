package ru.develop.schedule.domain;

import jakarta.persistence.*;
import ru.develop.schedule.domain.enums.Role;

@Entity
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

    public ProjectPerson(ProjectPersonId id, Project project, Person participant, Role role) {
        this.id = id;
        this.project = project;
        this.participant = participant;
        this.role = role;
    }

    public ProjectPerson() {
    }

    public ProjectPersonId getId() {
        return this.id;
    }

    public Project getProject() {
        return this.project;
    }

    public Person getParticipant() {
        return this.participant;
    }

    public Role getRole() {
        return this.role;
    }

    public void setId(ProjectPersonId id) {
        this.id = id;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public void setParticipant(Person participant) {
        this.participant = participant;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof ProjectPerson)) return false;
        final ProjectPerson other = (ProjectPerson) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$project = this.getProject();
        final Object other$project = other.getProject();
        if (this$project == null ? other$project != null : !this$project.equals(other$project)) return false;
        final Object this$participant = this.getParticipant();
        final Object other$participant = other.getParticipant();
        if (this$participant == null ? other$participant != null : !this$participant.equals(other$participant))
            return false;
        final Object this$role = this.getRole();
        final Object other$role = other.getRole();
        if (this$role == null ? other$role != null : !this$role.equals(other$role)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ProjectPerson;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $project = this.getProject();
        result = result * PRIME + ($project == null ? 43 : $project.hashCode());
        final Object $participant = this.getParticipant();
        result = result * PRIME + ($participant == null ? 43 : $participant.hashCode());
        final Object $role = this.getRole();
        result = result * PRIME + ($role == null ? 43 : $role.hashCode());
        return result;
    }

    public String toString() {
        return "ProjectPerson(id=" + this.getId() + ", project=" + this.getProject() + ", participant=" + this.getParticipant() + ", role=" + this.getRole() + ")";
    }
}
