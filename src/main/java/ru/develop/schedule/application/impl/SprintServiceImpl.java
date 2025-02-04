package ru.develop.schedule.application.impl;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.develop.schedule.application.services.ProjectPersonService;
import ru.develop.schedule.application.services.ProjectService;
import ru.develop.schedule.application.services.SprintService;
import ru.develop.schedule.domain.Sprint;
import ru.develop.schedule.domain.enums.Role;
import ru.develop.schedule.extern.exceptions.NoPermissionException;
import ru.develop.schedule.extern.repositories.SprintRepository;

import java.util.List;
import java.util.Set;

@Service
public class SprintServiceImpl implements SprintService {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(SprintServiceImpl.class);
    private final SprintRepository sprintRepository;
    private final ProjectService projectService;

    private final ProjectPersonService projectPersonService;

    public SprintServiceImpl(SprintRepository sprintRepository, ProjectService projectService, ProjectPersonService projectPersonService) {
        this.sprintRepository = sprintRepository;
        this.projectService = projectService;
        this.projectPersonService = projectPersonService;
    }

    @Transactional(readOnly = true)
    @Override
    public Sprint findSprintById(Long sprintId) {
        return sprintRepository.findById(sprintId)
                .orElseThrow(() -> {
                    log.error("Sprint with id: {} not found", sprintId);
                    return new NullPointerException("Sprint with id:" + sprintId + "not found");
                });
    }

    @Transactional
    @Override
    public void createSprint(Sprint sprint, Long personId, Long projectId) throws NoPermissionException {
        projectPersonService.checkPermission(Set.of(Role.ROLE_ADMIN, Role.ROLE_SUPERVISOR, Role.ROLE_STUDENT), personId, projectId);

        projectService.findProjectById(projectId).getSprint().add(sprint);
        sprintRepository.save(sprint);
        log.info("Sprint created");
    }

    @Transactional
    @Override
    public void deleteSprint(Long sprintId, Long projectId, Long personId) throws NoPermissionException {
        projectPersonService.checkPermission(Set.of(Role.ROLE_ADMIN, Role.ROLE_SUPERVISOR), personId, projectId);
        sprintRepository.deleteById(sprintId);
        log.info("Sprint with id {} deleted", sprintId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Sprint> getAllSprintByProjectId(Long projectId) {
        log.info("Project with id = {} get all sprints", projectId);

        return projectService.findProjectById(projectId).getSprint();
    }
}
