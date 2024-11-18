package ru.develop.schedule.extern.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record SprintDTO(Long id,
                        @NotNull @NotEmpty String title,
                        @FutureOrPresent LocalDate startDate,
                        @FutureOrPresent LocalDate endDate,
                        @NotNull @NotEmpty Long projectId) {
}
