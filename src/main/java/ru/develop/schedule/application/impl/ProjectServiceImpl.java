package ru.develop.schedule.application.impl;

import ch.qos.logback.core.util.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.develop.schedule.extern.repositories.ProjectRepository;
import ru.develop.schedule.domain.Person;
import ru.develop.schedule.domain.Project;
import ru.develop.schedule.domain.Sprint;
import ru.develop.schedule.application.services.ProjectService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    @Override
    public Project findProjectById(Long id) {
        return projectRepository.findById(id).orElseThrow(() -> {
            log.error("Project with id {} not found", id);
            return new NullPointerException("Project not found");
        });
    }

    @Override
    public List<Sprint> findAllSprintByProjectId(Long projectId) {
        return findProjectById(projectId).getSprint();
    }

    @Transactional
    @Override
    public void createProject(Project project) {
        projectRepository.save(project);
        log.info("Project created");
    }

    @Transactional
    @Override
    public void deleteProject(Long projectId) {
        projectRepository.delete(findProjectById(projectId));
        log.info("Project with id {} deleted", projectId);
    }

    @Transactional
    @Override
    public void updateProject(Long projectId, String boardName) {
        Project project = findProjectById(projectId);
        if (StringUtil.isNullOrEmpty(boardName)) {
            log.error("Board name cannot be null or empty");
            throw new IllegalArgumentException("Board name cannot be null or empty");
        }
        project.setBoardName(boardName);

        projectRepository.save(project);
        log.info("Project with id = {} update", projectId);
    }

    @Transactional
    @Override
    public void addPersonForProject(Long projectId, List<Person> persons) {
        Project project = findProjectById(projectId);
        project.setPeople(persons);
        log.info("Project with id = {} added persons", projectId);

        projectRepository.save(project);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Sprint> getAllSprintByProjectId(Long projectId) {
        log.info("Project with id = {} get all sprints", projectId);

        return findProjectById(projectId).getSprint();
    }
}
