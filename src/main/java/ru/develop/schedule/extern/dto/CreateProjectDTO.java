package ru.develop.schedule.extern.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateProjectDTO(
        List<Long> usersId,
        @NotNull @NotEmpty String boardName
) {
}
