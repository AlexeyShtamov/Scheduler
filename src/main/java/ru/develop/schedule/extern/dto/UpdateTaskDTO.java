package ru.develop.schedule.extern.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import ru.develop.schedule.domain.Person;
import ru.develop.schedule.domain.Sprint;
import ru.develop.schedule.domain.enums.Priority;
import ru.develop.schedule.domain.enums.Status;

import java.time.LocalDate;

public record UpdateTaskDTO(
        @NotNull @NotEmpty Long id,
        @NotNull @NotEmpty String title,
        String description,
        @NotNull @NotEmpty Priority priority,
        LocalDate assignedDate,
        LocalDate deadline,
        @NotEmpty double ttz,
        @NotNull @NotEmpty Status status,
        String review,
        Person worker,
        @NotNull Person author,
        @NotNull Sprint sprint
) {
}
