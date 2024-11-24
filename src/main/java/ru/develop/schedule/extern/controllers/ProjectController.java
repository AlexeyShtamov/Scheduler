package ru.develop.schedule.extern.controllers;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.develop.schedule.application.services.PersonService;
import ru.develop.schedule.application.services.ProjectPersonService;
import ru.develop.schedule.application.services.ProjectService;
import ru.develop.schedule.domain.Person;
import ru.develop.schedule.domain.Project;
import ru.develop.schedule.extern.dto.ProjectDTO;
import ru.develop.schedule.extern.mapper.ProjectMapper;

@RestController
@RequestMapping("api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final ProjectMapper projectMapper;
    private final PersonService personService;
    private final ProjectPersonService projectPersonService;

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

    @SneakyThrows
    @PutMapping("/{projectId}")
    public ResponseEntity<Void> updateProject(@PathVariable Long projectId, @RequestBody Long userID, @RequestBody String description) {
        projectService.updateProject(projectId, userID, description);

        return ResponseEntity.noContent().build();
    }

    @SneakyThrows
    @PutMapping("/{projectId}/people")
    public ResponseEntity<Void> addPersonForProject(@PathVariable Long projectId, @RequestBody Long userID, @RequestBody String emails) {
        Person person = personService.findByEmail(emails);
        projectService.addPersonForProject(projectId, userID, person);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{projectId}")
    public ResponseEntity<Void> addSpecialPersonForProject(@PathVariable Long projectId, @RequestBody Long userID,
                                                           @RequestBody String role) {
        projectPersonService.addSpecialPerson(projectId, userID, role);

        return ResponseEntity.noContent().build();
    }
}
