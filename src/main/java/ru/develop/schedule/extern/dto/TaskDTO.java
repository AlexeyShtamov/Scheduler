package ru.develop.schedule.extern.dto;

import ru.develop.schedule.domain.enums.Priority;
import ru.develop.schedule.domain.enums.Status;

import java.time.LocalDate;

public record TaskDTO(Long id,
                      String title,
                      String description,
                      Priority priority,
                      LocalDate startDate,
                      LocalDate endDate,
                      String assignee,
                      String author,
                      String ttz,
                      SprintDTO sprint,
                      Status status,
                      String review) {
}

