package ru.develop.schedule.application.services;

import ru.develop.schedule.domain.Person;
import ru.develop.schedule.domain.Task;
import ru.develop.schedule.domain.enums.Status;
import ru.develop.schedule.extern.dto.UpdateTaskDTO;

import java.util.List;

public interface TasksService {
    List<Task> findAllTaskBySprintAndState(Long sprintUuid, Status state);

    Task findTaskById(Long taskId);

    List<Task> findAllTaskBySprintIdAndPerson(Long sprintId, Long personId);

    void createTask(Person currentPerson, Task task);

    void updateTask(Long taskId, UpdateTaskDTO updatedTask);

    void deleteTask(Long taskId);
}
