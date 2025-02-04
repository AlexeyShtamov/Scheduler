package ru.develop.schedule.extern.controllers;

import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.develop.schedule.application.services.PersonService;
import ru.develop.schedule.application.services.ProjectPersonService;
import ru.develop.schedule.application.services.ProjectService;
import ru.develop.schedule.domain.Person;
import ru.develop.schedule.domain.Project;
import ru.develop.schedule.extern.dto.ProjectDTO;
import ru.develop.schedule.extern.exceptions.NoPermissionException;
import ru.develop.schedule.extern.mapper.ProjectMapper;

@RestController
@RequestMapping("api/projects")
public class ProjectController {

    private final ProjectService projectService;
    private final ProjectMapper projectMapper;
    private final PersonService personService;
    private final ProjectPersonService projectPersonService;

    public ProjectController(ProjectService projectService, ProjectMapper projectMapper, PersonService personService, ProjectPersonService projectPersonService) {
        this.projectService = projectService;
        this.projectMapper = projectMapper;
        this.personService = personService;
        this.projectPersonService = projectPersonService;
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectDTO> getProject(@PathVariable Long projectId) {
        Project project = projectService.findProjectById(projectId);
        ProjectDTO projectDto = projectMapper.projectToDTO(project);

        return ResponseEntity.ok(projectDto);
    }

    @PostMapping
    public ResponseEntity<Void> createProject(@RequestBody ProjectDTO projectDto) {
        projectService.createProject(projectMapper.dtoToProject(projectDto));

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long projectId) {
        projectService.deleteProject(projectId);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{projectId}")
    public ResponseEntity<Void> updateProject(@PathVariable Long projectId, @RequestBody Long userID, @RequestBody String description) throws NoPermissionException {
        projectService.updateProject(projectId, userID, description);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{projectId}/people")
    public ResponseEntity<Void> addPersonForProject(@PathVariable Long projectId, @RequestBody Long userID, @RequestBody String emails, @RequestParam String role) {
        Person person = personService.findByEmail(emails);
        projectPersonService.addSpecialPerson(userID, projectId, person, role);

        return ResponseEntity.noContent().build();
    }
}
