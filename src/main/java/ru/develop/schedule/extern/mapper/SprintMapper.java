package ru.develop.schedule.extern.mapper;

import org.springframework.stereotype.Component;
import ru.develop.schedule.application.services.ProjectService;
import ru.develop.schedule.domain.Sprint;
import ru.develop.schedule.extern.dto.SprintDTO;

import java.util.List;

@Component
public class SprintMapper {
    private final ProjectService projectService;

    public SprintMapper(ProjectService projectService) {
        this.projectService = projectService;
    }

    public Sprint getSprintFromDto(SprintDTO sprintDTO) {
        return new Sprint(null, sprintDTO.title(),
                sprintDTO.startDate(),
                sprintDTO.endDate(),
                List.of(),
                projectService.findProjectById(sprintDTO.projectId()));
    }

    public SprintDTO getSprintDTOFromSprint(Sprint sprint) {
        return new SprintDTO(
                sprint.getId(),
                sprint.getTitle(),
                sprint.getStartTime(),
                sprint.getEndTime(),
                sprint.getProject().getId()
        );
    }
}
