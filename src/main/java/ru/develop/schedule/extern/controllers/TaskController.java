package ru.develop.schedule.extern.controllers;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.develop.schedule.application.services.PersonService;
import ru.develop.schedule.application.services.TasksService;
import ru.develop.schedule.domain.Person;
import ru.develop.schedule.domain.Task;
import ru.develop.schedule.domain.enums.Status;
import ru.develop.schedule.extern.dto.TaskDTO;
import ru.develop.schedule.extern.dto.UpdateTaskDTO;
import ru.develop.schedule.extern.mapper.TaskMapper;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/tasks")
public class TaskController {

    private final TasksService tasksService;
    private final TaskMapper taskMapper;
    private final PersonService personService;

    @GetMapping(path = "/{taskId}")
    public ResponseEntity<TaskDTO> getTask(@PathVariable Long taskId) {

        return ResponseEntity.ok(taskMapper.getTaskDTOFromTask(tasksService.findTaskById(taskId)));
    }

    @GetMapping
    public ResponseEntity<List<TaskDTO>> getTasksForSprintAndState(@RequestParam Long sprintId,
                                                                   @RequestParam Status status) {
        List<TaskDTO> listTaskDto = tasksService.findAllTaskBySprintAndState(sprintId, status)
                .stream()
                .map(taskMapper::getTaskDTOFromTask)
                .toList();

        return ResponseEntity.ok(listTaskDto);
    }

    @GetMapping("/sprints/{sprintId}")
    public ResponseEntity<List<TaskDTO>> getTasksForSprint(@PathVariable Long sprintId) {
        List<TaskDTO> listTaskDto = tasksService.findAllTaskBySprint(sprintId)
                .stream()
                .map(taskMapper::getTaskDTOFromTask)
                .toList();

        return ResponseEntity.ok(listTaskDto);
    }

    @SneakyThrows
    @PostMapping
    public ResponseEntity<Void> createTask(@RequestBody TaskDTO taskDTO, @RequestParam Long userId, @RequestParam Long projectId) {
        Person person = personService.findPersonById(userId);
        Task task = taskMapper.getTaskFromTaskDTO(taskDTO);
        tasksService.createTask(person, task, projectId, userId);

        return ResponseEntity.noContent().build();
    }

    @SneakyThrows
    @PutMapping
    public ResponseEntity<Void> updateTask(@RequestBody UpdateTaskDTO taskDTO, @RequestParam Long userId, @RequestParam Long projectId) {
        tasksService.updateTask(taskDTO.id(), taskDTO, projectId, userId);

        return ResponseEntity.noContent().build();
    }

    @SneakyThrows
    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId, @RequestParam Long projectId, @RequestParam Long userId) {
        tasksService.deleteTask(taskId, projectId, userId);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<Void> changeStatus(@PathVariable Long taskId, @RequestParam Status status,
                                             @RequestParam Long projectId, @RequestParam Long userId) {
        tasksService.changeStatus(taskId, status, projectId, userId);

        return ResponseEntity.noContent().build();
    }

    @SneakyThrows
    @PutMapping("rewiew/{taskId}")
    public ResponseEntity<Void> reviewTask(@PathVariable Long taskId, @RequestParam Long projectId,
                                           @RequestParam Long userId, @RequestParam String comment) {
        tasksService.reviewTask(taskId, comment, projectId, userId);

        return ResponseEntity.noContent().build();
    }
}
