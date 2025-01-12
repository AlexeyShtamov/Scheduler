package ru.develop.schedule.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.develop.schedule.domain.enums.Priority;
import ru.develop.schedule.domain.enums.Status;

import java.time.LocalDate;

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
}
