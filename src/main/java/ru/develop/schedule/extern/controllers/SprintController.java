package ru.develop.schedule.extern.controllers;

import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.develop.schedule.application.services.SprintService;
import ru.develop.schedule.extern.dto.SprintDTO;
import ru.develop.schedule.extern.exceptions.NoPermissionException;
import ru.develop.schedule.extern.mapper.SprintMapper;

import java.util.List;

@RestController
@RequestMapping("api/sprints")
public class SprintController {
    private final SprintService sprintService;
    private final SprintMapper sprintMapper;

    public SprintController(SprintService sprintService, SprintMapper sprintMapper) {
        this.sprintService = sprintService;
        this.sprintMapper = sprintMapper;
    }

    @GetMapping("/{sprintId}")
    public ResponseEntity<SprintDTO> getSprint(@PathVariable Long sprintId) {
        SprintDTO sprintDTO = sprintMapper.getSprintDTOFromSprint(sprintService.findSprintById(sprintId));

        return ResponseEntity.ok(sprintDTO);
    }

    @PostMapping
    public ResponseEntity<SprintDTO> createSprint(@RequestBody SprintDTO sprintDTO, @RequestParam Long personID) throws NoPermissionException {
        sprintService.createSprint(sprintMapper.getSprintFromDto(sprintDTO), personID, sprintDTO.projectId());

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
