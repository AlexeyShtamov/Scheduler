package ru.develop.schedule.extern.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.develop.schedule.application.services.ProjectService;
import ru.develop.schedule.domain.Project;
import ru.develop.schedule.extern.dto.ProjectDTO;

@RestController
@RequestMapping("api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectDTO> getAllProjects(@PathVariable Long projectId) {
        Project project = projectService.findProjectById(projectId);
        ProjectDTO projectDto = new ProjectDTO(project.getId(), project.getBoardName());

        return ResponseEntity.ok(projectDto);
    }
}
