package ru.develop.schedule.domain;

import jakarta.persistence.*;
import ru.develop.schedule.domain.enums.Priority;
import ru.develop.schedule.domain.enums.Status;

import java.time.LocalDate;

/**
 * Задача в рамках определенного проекта-доски
 */
@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * dasdas
     */
    private String title;
    private String description;
    @Enumerated(EnumType.STRING)
    private Priority priority;
    private LocalDate createDate = LocalDate.now();
    private LocalDate assignDate;
    private LocalDate deadline;
    /**
     * Трудозатраты в часах
     */
    private String ttz = "";
    @Enumerated(EnumType.STRING)
    private Status status;
    private String review;
    /**
     * Исполнитель
     */
    @ManyToOne
    private Person worker;

    @ManyToOne
    private Person author;

    @ManyToOne
    private Sprint sprint;

    public Task(Long id, String title, String description, Priority priority, LocalDate createDate, LocalDate assignDate, LocalDate deadline, String ttz, Status status, String review, Person worker, Person author, Sprint sprint) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.createDate = createDate;
        this.assignDate = assignDate;
        this.deadline = deadline;
        this.ttz = ttz;
        this.status = status;
        this.review = review;
        this.worker = worker;
        this.author = author;
        this.sprint = sprint;
    }

    public Task() {
    }

    public static TaskBuilder builder() {
        return new TaskBuilder();
    }

    public Long getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public Priority getPriority() {
        return this.priority;
    }

    public LocalDate getCreateDate() {
        return this.createDate;
    }

    public LocalDate getAssignDate() {
        return this.assignDate;
    }

    public LocalDate getDeadline() {
        return this.deadline;
    }

    public String getTtz() {
        return this.ttz;
    }

    public Status getStatus() {
        return this.status;
    }

    public String getReview() {
        return this.review;
    }

    public Person getWorker() {
        return this.worker;
    }

    public Person getAuthor() {
        return this.author;
    }

    public Sprint getSprint() {
        return this.sprint;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public void setAssignDate(LocalDate assignDate) {
        this.assignDate = assignDate;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public void setTtz(String ttz) {
        this.ttz = ttz;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public void setWorker(Person worker) {
        this.worker = worker;
    }

    public void setAuthor(Person author) {
        this.author = author;
    }

    public void setSprint(Sprint sprint) {
        this.sprint = sprint;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Task)) return false;
        final Task other = (Task) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$title = this.getTitle();
        final Object other$title = other.getTitle();
        if (this$title == null ? other$title != null : !this$title.equals(other$title)) return false;
        final Object this$description = this.getDescription();
        final Object other$description = other.getDescription();
        if (this$description == null ? other$description != null : !this$description.equals(other$description))
            return false;
        final Object this$priority = this.getPriority();
        final Object other$priority = other.getPriority();
        if (this$priority == null ? other$priority != null : !this$priority.equals(other$priority)) return false;
        final Object this$createDate = this.getCreateDate();
        final Object other$createDate = other.getCreateDate();
        if (this$createDate == null ? other$createDate != null : !this$createDate.equals(other$createDate))
            return false;
        final Object this$assignDate = this.getAssignDate();
        final Object other$assignDate = other.getAssignDate();
        if (this$assignDate == null ? other$assignDate != null : !this$assignDate.equals(other$assignDate))
            return false;
        final Object this$deadline = this.getDeadline();
        final Object other$deadline = other.getDeadline();
        if (this$deadline == null ? other$deadline != null : !this$deadline.equals(other$deadline)) return false;
        final Object this$ttz = this.getTtz();
        final Object other$ttz = other.getTtz();
        if (this$ttz == null ? other$ttz != null : !this$ttz.equals(other$ttz)) return false;
        final Object this$status = this.getStatus();
        final Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        final Object this$review = this.getReview();
        final Object other$review = other.getReview();
        if (this$review == null ? other$review != null : !this$review.equals(other$review)) return false;
        final Object this$worker = this.getWorker();
        final Object other$worker = other.getWorker();
        if (this$worker == null ? other$worker != null : !this$worker.equals(other$worker)) return false;
        final Object this$author = this.getAuthor();
        final Object other$author = other.getAuthor();
        if (this$author == null ? other$author != null : !this$author.equals(other$author)) return false;
        final Object this$sprint = this.getSprint();
        final Object other$sprint = other.getSprint();
        if (this$sprint == null ? other$sprint != null : !this$sprint.equals(other$sprint)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Task;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $title = this.getTitle();
        result = result * PRIME + ($title == null ? 43 : $title.hashCode());
        final Object $description = this.getDescription();
        result = result * PRIME + ($description == null ? 43 : $description.hashCode());
        final Object $priority = this.getPriority();
        result = result * PRIME + ($priority == null ? 43 : $priority.hashCode());
        final Object $createDate = this.getCreateDate();
        result = result * PRIME + ($createDate == null ? 43 : $createDate.hashCode());
        final Object $assignDate = this.getAssignDate();
        result = result * PRIME + ($assignDate == null ? 43 : $assignDate.hashCode());
        final Object $deadline = this.getDeadline();
        result = result * PRIME + ($deadline == null ? 43 : $deadline.hashCode());
        final Object $ttz = this.getTtz();
        result = result * PRIME + ($ttz == null ? 43 : $ttz.hashCode());
        final Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final Object $review = this.getReview();
        result = result * PRIME + ($review == null ? 43 : $review.hashCode());
        final Object $worker = this.getWorker();
        result = result * PRIME + ($worker == null ? 43 : $worker.hashCode());
        final Object $author = this.getAuthor();
        result = result * PRIME + ($author == null ? 43 : $author.hashCode());
        final Object $sprint = this.getSprint();
        result = result * PRIME + ($sprint == null ? 43 : $sprint.hashCode());
        return result;
    }

    public String toString() {
        return "Task(id=" + this.getId() + ", title=" + this.getTitle() + ", description=" + this.getDescription() + ", priority=" + this.getPriority() + ", createDate=" + this.getCreateDate() + ", assignDate=" + this.getAssignDate() + ", deadline=" + this.getDeadline() + ", ttz=" + this.getTtz() + ", status=" + this.getStatus() + ", review=" + this.getReview() + ", worker=" + this.getWorker() + ", author=" + this.getAuthor() + ", sprint=" + this.getSprint() + ")";
    }

    public static class TaskBuilder {
        private Long id;
        private String title;
        private String description;
        private Priority priority;
        private LocalDate createDate;
        private LocalDate assignDate;
        private LocalDate deadline;
        private String ttz;
        private Status status;
        private String review;
        private Person worker;
        private Person author;
        private Sprint sprint;

        TaskBuilder() {
        }

        public TaskBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public TaskBuilder title(String title) {
            this.title = title;
            return this;
        }

        public TaskBuilder description(String description) {
            this.description = description;
            return this;
        }

        public TaskBuilder priority(Priority priority) {
            this.priority = priority;
            return this;
        }

        public TaskBuilder createDate(LocalDate createDate) {
            this.createDate = createDate;
            return this;
        }

        public TaskBuilder assignDate(LocalDate assignDate) {
            this.assignDate = assignDate;
            return this;
        }

        public TaskBuilder deadline(LocalDate deadline) {
            this.deadline = deadline;
            return this;
        }

        public TaskBuilder ttz(String ttz) {
            this.ttz = ttz;
            return this;
        }

        public TaskBuilder status(Status status) {
            this.status = status;
            return this;
        }

        public TaskBuilder review(String review) {
            this.review = review;
            return this;
        }

        public TaskBuilder worker(Person worker) {
            this.worker = worker;
            return this;
        }

        public TaskBuilder author(Person author) {
            this.author = author;
            return this;
        }

        public TaskBuilder sprint(Sprint sprint) {
            this.sprint = sprint;
            return this;
        }

        public Task build() {
            return new Task(this.id, this.title, this.description, this.priority, this.createDate, this.assignDate, this.deadline, this.ttz, this.status, this.review, this.worker, this.author, this.sprint);
        }

        public String toString() {
            return "Task.TaskBuilder(id=" + this.id + ", title=" + this.title + ", description=" + this.description + ", priority=" + this.priority + ", createDate=" + this.createDate + ", assignDate=" + this.assignDate + ", deadline=" + this.deadline + ", ttz=" + this.ttz + ", status=" + this.status + ", review=" + this.review + ", worker=" + this.worker + ", author=" + this.author + ", sprint=" + this.sprint + ")";
        }
    }
}
