package ru.develop.schedule.application.services;

import ru.develop.schedule.domain.Person;
import ru.develop.schedule.domain.Project;
import ru.develop.schedule.domain.Sprint;

import java.util.List;

public interface ProjectService {
    Project findProjectById(Long id);

    void createProject(Project project);

    void deleteProject(Long projectId);

    void updateProject(Long projectId, String boardName);

    void addPersonForProject(Long projectId, List<Person> persons);
}
