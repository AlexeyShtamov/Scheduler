package ru.develop.schedule.domain;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tbl_sprint")
public class Sprint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = false, nullable = false, length = 255)
    private String title;

    /**
     * Дата начала спринта
     */
    @Column(nullable = false)
    private LocalDate startTime;

    /**
     * Дата конца спринта
     */
    @Column(nullable = false)
    private LocalDate endTime;

    @OneToMany(mappedBy = "sprint", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private List<Task> tasks = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Project project;

    public Sprint(Long id, String title, LocalDate startTime, LocalDate endTime, List<Task> tasks, Project project) {
        this.id = id;
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.tasks = tasks;
        this.project = project;
    }

    public Sprint() {
    }

    public Long getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public LocalDate getStartTime() {
        return this.startTime;
    }

    public LocalDate getEndTime() {
        return this.endTime;
    }

    public List<Task> getTasks() {
        return this.tasks;
    }

    public Project getProject() {
        return this.project;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStartTime(LocalDate startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalDate endTime) {
        this.endTime = endTime;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Sprint)) return false;
        final Sprint other = (Sprint) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$title = this.getTitle();
        final Object other$title = other.getTitle();
        if (this$title == null ? other$title != null : !this$title.equals(other$title)) return false;
        final Object this$startTime = this.getStartTime();
        final Object other$startTime = other.getStartTime();
        if (this$startTime == null ? other$startTime != null : !this$startTime.equals(other$startTime)) return false;
        final Object this$endTime = this.getEndTime();
        final Object other$endTime = other.getEndTime();
        if (this$endTime == null ? other$endTime != null : !this$endTime.equals(other$endTime)) return false;
        final Object this$tasks = this.getTasks();
        final Object other$tasks = other.getTasks();
        if (this$tasks == null ? other$tasks != null : !this$tasks.equals(other$tasks)) return false;
        final Object this$project = this.getProject();
        final Object other$project = other.getProject();
        if (this$project == null ? other$project != null : !this$project.equals(other$project)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Sprint;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $title = this.getTitle();
        result = result * PRIME + ($title == null ? 43 : $title.hashCode());
        final Object $startTime = this.getStartTime();
        result = result * PRIME + ($startTime == null ? 43 : $startTime.hashCode());
        final Object $endTime = this.getEndTime();
        result = result * PRIME + ($endTime == null ? 43 : $endTime.hashCode());
        final Object $tasks = this.getTasks();
        result = result * PRIME + ($tasks == null ? 43 : $tasks.hashCode());
        final Object $project = this.getProject();
        result = result * PRIME + ($project == null ? 43 : $project.hashCode());
        return result;
    }

    public String toString() {
        return "Sprint(id=" + this.getId() + ", title=" + this.getTitle() + ", startTime=" + this.getStartTime() + ", endTime=" + this.getEndTime() + ", tasks=" + this.getTasks() + ", project=" + this.getProject() + ")";
    }
}
