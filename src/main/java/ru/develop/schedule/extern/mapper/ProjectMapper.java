package ru.develop.schedule.extern.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.develop.schedule.application.services.PersonService;
import ru.develop.schedule.application.services.ProjectService;
import ru.develop.schedule.domain.Project;
import ru.develop.schedule.extern.dto.CreateProjectDTO;
import ru.develop.schedule.extern.dto.ProjectDTO;

@RequiredArgsConstructor
@Component
public class ProjectMapper {

    private final ProjectService projectService;
    private final PersonService personService;
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

    public Project dtoToProject(CreateProjectDTO createProjectDTO) {
        Project project = new Project();
        project.setBoardName(createProjectDTO.boardName());
        project.setPeople(personService.findAllById(createProjectDTO.usersId()));

        return project;
    }
}
