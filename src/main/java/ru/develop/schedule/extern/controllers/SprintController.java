package ru.develop.schedule.extern.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.develop.schedule.application.services.SprintService;
import ru.develop.schedule.extern.dto.SprintDTO;
import ru.develop.schedule.extern.mapper.SprintMapper;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/sprints")
public class SprintController {
    private final SprintService sprintService;
    private final SprintMapper sprintMapper;

    @GetMapping("/{sprintId}")
    public ResponseEntity<SprintDTO> getSprint(@PathVariable Long sprintId) {
        SprintDTO sprintDTO = sprintMapper.getSprintDTOFromSprint(sprintService.findSprintById(sprintId));

        return ResponseEntity.ok(sprintDTO);
    }

    @PostMapping
    public ResponseEntity<SprintDTO> createSprint(@RequestBody SprintDTO sprintDTO) {
        sprintService.createSprint(sprintMapper.getSprintFromDto(sprintDTO));

        return ResponseEntity.ok(sprintDTO);
    }

    @GetMapping
    public ResponseEntity<List<SprintDTO>> getAllSprintsByProject(@RequestParam Long projectId) {
        List<SprintDTO> sprints = sprintService.getAllSprintByProjectId(projectId)
                .stream()
                .map(sprintMapper::getSprintDTOFromSprint)
                .toList();

        return ResponseEntity.ok(sprints);
    }

    @DeleteMapping("/{sprintId}")
    public ResponseEntity<Void> deleteSprint(@PathVariable Long sprintId) {
        sprintService.deleteSprint(sprintId);

        return ResponseEntity.noContent().build();
    }

}
