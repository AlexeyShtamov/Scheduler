package ru.develop.schedule.extern.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ProjectDTO(
        @NotEmpty Long id,
        @NotEmpty @NotNull String boardName,
        List<InfoPersonDTO> users,
        List<SprintDTO> sprints) {
}
