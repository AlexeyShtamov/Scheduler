package ru.develop.schedule.extern.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ProjectDTO(
        @NotEmpty Long id,
        @NotEmpty @NotNull String boardName
) {
}
