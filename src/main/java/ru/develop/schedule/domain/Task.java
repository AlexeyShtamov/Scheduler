package ru.develop.schedule.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.develop.schedule.domain.enums.Priority;
import ru.develop.schedule.domain.enums.Status;

import java.time.LocalDate;
import java.util.List;

/**
 * Задача в рамках определенного проекта-доски
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private Status status;
    private Priority priority;
    private LocalDate createTime;
    private LocalDate deadline;
    private String review;

    @ManyToOne
    private Project project;

    @ManyToMany
    private List<Person> people;
}
