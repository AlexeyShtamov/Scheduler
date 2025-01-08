package ru.develop.schedule.extern.mapper;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.develop.schedule.domain.Project;
import ru.develop.schedule.extern.dto.ProjectDTO;

@Component
public class ProjectMapper {
    private final PersonMapper personMapper;
    private final SprintMapper sprintMapper;

    public ProjectMapper(@Lazy PersonMapper personMapper,
                         SprintMapper sprintMapper) {
        this.personMapper = personMapper;
        this.sprintMapper = sprintMapper;
    }

    public ProjectDTO projectToDTO(Project project) {
        return new ProjectDTO(
                project.getId(),
                project.getBoardName(),
                project.getPeople().stream().map(personMapper::fromPersonToDTO).toList(),
                project.getSprint().stream().map(sprintMapper::getSprintDTOFromSprint).toList()
        );
    }

    public Project dtoToProject(ProjectDTO projectDTO) {
        Project project = new Project();
        project.setBoardName(projectDTO.boardName());

        return project;
    }
}