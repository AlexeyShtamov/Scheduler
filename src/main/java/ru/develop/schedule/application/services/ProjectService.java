package ru.develop.schedule.application.services;

import ru.develop.schedule.domain.Person;
import ru.develop.schedule.domain.Project;
import ru.develop.schedule.domain.Sprint;
import ru.develop.schedule.extern.exceptions.NoPermissionException;

import java.util.List;

public interface ProjectService {
    Project findProjectById(Long id);

    void createProject(Project project);

    void deleteProject(Long projectId);

    void updateProject(Long projectId, Long personId, String boardName) throws NoPermissionException;

    void addPersonForProject(Long projectId, Long personId, List<Person> persons) throws NoPermissionException;
}
