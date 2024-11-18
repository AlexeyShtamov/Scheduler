package ru.develop.schedule.application.services;

import ru.develop.schedule.domain.Sprint;
import ru.develop.schedule.extern.exceptions.NoPermissionException;

import java.util.List;

public interface SprintService {
    Sprint findSprintById(Long sprintId);

    void createSprint(Sprint sprint, Long personId, Long projectId) throws NoPermissionException;

    void deleteSprint(Long sprintId, Long projectId, Long personId) throws NoPermissionException;
}
