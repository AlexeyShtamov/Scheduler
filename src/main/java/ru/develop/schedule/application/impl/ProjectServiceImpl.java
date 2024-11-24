package ru.develop.schedule.application.impl;

import ch.qos.logback.core.util.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.develop.schedule.application.services.ProjectPersonService;
import ru.develop.schedule.application.services.ProjectService;
import ru.develop.schedule.domain.Person;
import ru.develop.schedule.domain.Project;
import ru.develop.schedule.domain.Sprint;
import ru.develop.schedule.domain.enums.Role;
import ru.develop.schedule.extern.exceptions.NoPermissionException;
import ru.develop.schedule.extern.repositories.ProjectRepository;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    private final ProjectPersonService projectPersonService;

    @Override
    public Project findProjectById(Long id) {
        return projectRepository.findById(id).orElseThrow(() -> {
            log.error("Project with id {} not found", id);
            return new NullPointerException("Project not found");
        });
    }

    @Transactional
    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void createProject(Project project) {
        projectRepository.save(project);
        log.info("Project created");
    }

    @Transactional
    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteProject(Long projectId) {
        projectRepository.delete(findProjectById(projectId));
        log.info("Project with id {} deleted", projectId);
    }

    @Transactional
    @Override
    public void updateProject(Long projectId, Long personId, String boardName) throws NoPermissionException {

        projectPersonService.checkPermission(Set.of(Role.ROLE_ADMIN, Role.ROLE_SUPERVISOR), projectId, personId);

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
    public void addPersonForProject(Long projectId, Long personId, Person person) throws NoPermissionException {

        projectPersonService.checkPermission(Set.of(Role.ROLE_ADMIN, Role.ROLE_SUPERVISOR), projectId, personId);

        Project project = findProjectById(projectId);
        project.setPeople(person);
        log.info("Project with id = {} added person", projectId);

        projectRepository.save(project);
    }
}