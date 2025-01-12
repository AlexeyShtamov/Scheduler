package ru.develop.schedule.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Проект (или же доска проекта), имеющий список участников-команду и задачи
 */
@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String boardName;

    /**
     * участники
     */
    @ManyToMany(cascade = CascadeType.PERSIST)
    private List<Person> people = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private List<Sprint> sprint = new ArrayList<>();

    public Project(Long id, String boardName, List<Person> people, List<Sprint> sprint) {
        this.id = id;
        this.boardName = boardName;
        this.people = people;
        this.sprint = sprint;
    }

    public Project() {
    }

    public static ProjectBuilder builder() {
        return new ProjectBuilder();
    }

    public void setPeople(Person people) {
        this.people.add(people);
    }

    public void setSprint(List<Sprint> sprint) {
        this.sprint.addAll(sprint);
    }

    public Long getId() {
        return this.id;
    }

    public String getBoardName() {
        return this.boardName;
    }

    public List<Person> getPeople() {
        return this.people;
    }

    public List<Sprint> getSprint() {
        return this.sprint;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Project)) return false;
        final Project other = (Project) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$boardName = this.getBoardName();
        final Object other$boardName = other.getBoardName();
        if (this$boardName == null ? other$boardName != null : !this$boardName.equals(other$boardName)) return false;
        final Object this$people = this.getPeople();
        final Object other$people = other.getPeople();
        if (this$people == null ? other$people != null : !this$people.equals(other$people)) return false;
        final Object this$sprint = this.getSprint();
        final Object other$sprint = other.getSprint();
        if (this$sprint == null ? other$sprint != null : !this$sprint.equals(other$sprint)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Project;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $boardName = this.getBoardName();
        result = result * PRIME + ($boardName == null ? 43 : $boardName.hashCode());
        final Object $people = this.getPeople();
        result = result * PRIME + ($people == null ? 43 : $people.hashCode());
        final Object $sprint = this.getSprint();
        result = result * PRIME + ($sprint == null ? 43 : $sprint.hashCode());
        return result;
    }

    public String toString() {
        return "Project(id=" + this.getId() + ", boardName=" + this.getBoardName() + ", people=" + this.getPeople() + ", sprint=" + this.getSprint() + ")";
    }

    public static class ProjectBuilder {
        private Long id;
        private String boardName;
        private List<Person> people;
        private List<Sprint> sprint;

        ProjectBuilder() {
        }

        public ProjectBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ProjectBuilder boardName(String boardName) {
            this.boardName = boardName;
            return this;
        }

        public ProjectBuilder people(List<Person> people) {
            this.people = people;
            return this;
        }

        public ProjectBuilder sprint(List<Sprint> sprint) {
            this.sprint = sprint;
            return this;
        }

        public Project build() {
            return new Project(this.id, this.boardName, this.people, this.sprint);
        }

        public String toString() {
            return "Project.ProjectBuilder(id=" + this.id + ", boardName=" + this.boardName + ", people=" + this.people + ", sprint=" + this.sprint + ")";
        }
    }
}
