package ru.develop.schedule.application.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.develop.schedule.application.services.ProjectService;
import ru.develop.schedule.application.services.SprintService;
import ru.develop.schedule.domain.Sprint;
import ru.develop.schedule.extern.repositories.SprintRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SprintServiceImpl implements SprintService {

    private final SprintRepository sprintRepository;
    private final ProjectService projectService;

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
    public void createSprint(Sprint sprint) {
        sprintRepository.save(sprint);
        log.info("Sprint created");
    }

    @Transactional
    @Override
    public void deleteSprint(Long sprintId) {
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
