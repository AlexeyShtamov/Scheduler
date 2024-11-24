package ru.develop.schedule.extern.controllers;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.develop.schedule.application.services.SprintService;
import ru.develop.schedule.domain.Sprint;
import ru.develop.schedule.extern.dto.SprintDTO;
import ru.develop.schedule.extern.exceptions.NoPermissionException;
import ru.develop.schedule.extern.mapper.SprintMapper;

import java.io.Serializable;
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

    @SneakyThrows
    @PostMapping
    public ResponseEntity<SprintDTO> createSprint(@RequestBody SprintDTO sprintDTO, @RequestParam Long personID ) {
        sprintService.createSprint(sprintMapper.getSprintFromDto(sprintDTO),personID,sprintDTO.projectId());

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
    public ResponseEntity<Void> deleteSprint(@PathVariable Long sprintId,
                                             @RequestParam Long projectId,
                                             @RequestParam Long personId) {
        try {
            sprintService.deleteSprint(sprintId, projectId, personId);

            return ResponseEntity.noContent().build();
        } catch (NoPermissionException e) {
            return ResponseEntity.status(403).build();
        }

    }

}
