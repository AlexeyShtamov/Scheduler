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
     * gkkdffnksdlfds
     */
    private String title;
    private String description;
    private Priority priority;
    private LocalDate createDate = LocalDate.now();
    private LocalDate assignDate;
    private LocalDate deadline;
    /**
     * Трудозатраты
     */
    private double ttz;
    private Status status;
    //TODO
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