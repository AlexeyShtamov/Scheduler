package ru.develop.schedule.application.services;

import ru.develop.schedule.domain.Sprint;

import java.util.List;

public interface SprintService {
    Sprint findSprintById(Long sprintId);

    void createSprint(Sprint sprint);

    void deleteSprint(Long sprintId);

    List<Sprint> getAllSprintByProjectId(Long projectId);
}
