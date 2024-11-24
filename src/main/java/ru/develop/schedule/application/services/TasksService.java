package ru.develop.schedule.application.services;

import ru.develop.schedule.domain.Person;
import ru.develop.schedule.domain.Task;
import ru.develop.schedule.domain.enums.Status;
import ru.develop.schedule.extern.dto.UpdateTaskDTO;
import ru.develop.schedule.extern.exceptions.NoPermissionException;

import java.util.List;

public interface TasksService {
    List<Task> findAllTaskBySprintAndState(Long sprintUuid, Status state);

    Task findTaskById(Long taskId);

    List<Task> findAllTaskBySprint(Long sprintUuid);

    List<Task> findAllTaskBySprintIdAndPerson(Long sprintId, Long personId);

    void createTask(Person currentPerson, Task task, Long projectId, Long personId) throws NoPermissionException;

    void updateTask(Long taskId, UpdateTaskDTO updatedTask, Long projectId, Long personId) throws NoPermissionException;

    void deleteTask(Long taskId, Long projectId, Long personId) throws NoPermissionException;

    void reviewTask(Long taskId, String comment, Long projectId, Long personId) throws NoPermissionException;

    void changeStatus(Long taskId, Status status, Long projectId, Long personId);
}
