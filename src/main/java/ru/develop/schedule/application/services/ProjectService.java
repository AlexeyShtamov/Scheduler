package ru.develop.schedule.application.services;

import ru.develop.schedule.domain.Person;
import ru.develop.schedule.domain.Project;
import ru.develop.schedule.domain.Sprint;

import java.util.List;

public interface ProjectService {
    Project findProjectById(Long id);

    List<Sprint> findAllSprintByProjectId(Long projectId);

    void createProject(Project project);

    void deleteProject(Long projectId);

    void updateProject(Long projectId, String boardName);

    void addPersonForProject(Long projectId, List<Person> persons);

    List<Sprint> getAllSprintByProjectId(Long projectId);
}
