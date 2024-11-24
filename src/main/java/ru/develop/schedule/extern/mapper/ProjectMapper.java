package ru.develop.schedule.extern.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.develop.schedule.application.services.ProjectService;
import ru.develop.schedule.domain.Project;
import ru.develop.schedule.extern.dto.ProjectDTO;

@RequiredArgsConstructor
@Component
public class ProjectMapper {

    private final ProjectService projectService;
    private final PersonMapper personMapper;

    public ProjectDTO projectToDTO(Project project) {
        return new ProjectDTO(
                project.getId(),
                project.getBoardName(),
                project.getPeople().stream().map(personMapper::fromPersonToDTO).toList()
        );
    }

    public Project dtoToProject(ProjectDTO projectDTO) {
        Project project = new Project();
        project.setBoardName(projectDTO.boardName());

        return project;
    }
}
