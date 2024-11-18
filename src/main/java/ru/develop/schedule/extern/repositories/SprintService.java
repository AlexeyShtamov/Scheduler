package ru.develop.schedule.extern.repositories;

import ru.develop.schedule.domain.Sprint;

public interface SprintService {
    Sprint findSprintById(Long sprintId);

    void createSprint(Sprint sprint);

    void deleteSprint(Long sprintId);
}
