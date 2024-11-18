package ru.develop.schedule.application.services;

import ru.develop.schedule.domain.Sprint;

public interface SprintService {
    Sprint findSprintById(Long sprintId);

    void createSprint(Sprint sprint);

    void deleteSprint(Long sprintId);
}
