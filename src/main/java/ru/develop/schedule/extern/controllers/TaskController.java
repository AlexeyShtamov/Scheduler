package ru.develop.schedule.extern.controllers;

import lombok.RequiredArgsConstructor;
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

@RestController()
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

    @PostMapping
    public ResponseEntity<Void> createTask(@RequestBody TaskDTO taskDTO, @RequestParam Long userId) {
        Person person = personService.findPersonById(userId);
        Task task = taskMapper.getTaskFromTaskDTO(taskDTO);
        tasksService.createTask(person, task);

        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<Void> updateTask(@RequestBody UpdateTaskDTO taskDTO, @RequestParam Long userId) {
        //Person person = personService.findPersonById(userId);
        //TODO не знаю что будет по правам, может нужен будет user в запросе
        tasksService.updateTask(taskDTO.id(), taskDTO);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        tasksService.deleteTask(taskId);

        return ResponseEntity.noContent().build();
    }
}
