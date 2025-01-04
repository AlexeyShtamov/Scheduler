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
                      TaskPersonDto assignee,
                      TaskPersonDto author,
                      double ttz,
                      SprintDTO sprint,
                      Status status,
                      String review) {
}

