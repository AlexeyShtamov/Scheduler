package ru.develop.schedule.application.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.develop.schedule.application.services.ProjectPersonService;
import ru.develop.schedule.domain.enums.Role;
import ru.develop.schedule.extern.exceptions.NoPermissionException;
import ru.develop.schedule.extern.repositories.TasksRepository;
import ru.develop.schedule.domain.Person;
import ru.develop.schedule.domain.Task;
import ru.develop.schedule.domain.enums.Status;
import ru.develop.schedule.extern.dto.UpdateTaskDTO;
import ru.develop.schedule.application.services.SprintService;
import ru.develop.schedule.application.services.TasksService;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class TasksServiceImpl implements TasksService {

    private final TasksRepository tasksRepository;
    private final SprintService sprintService;
    private final ProjectPersonService projectPersonService;

    @Transactional(readOnly = true)
    @Override
    public List<Task> findAllTaskBySprintAndState(Long sprintUuid, Status state) {
        return sprintService.findSprintById(sprintUuid).getTasks().stream()
                .filter(task -> task.getStatus().equals(state)).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public Task findTaskById(Long taskId) {
        return tasksRepository.findById(taskId)
                .orElseThrow(() -> {
                    log.error("Task with id {} not found", taskId);
                    return new NullPointerException("Task with id " + taskId + "not found");
                });
    }

    @Transactional(readOnly = true)
    @Override
    public List<Task> findAllTaskBySprintIdAndPerson(Long sprintId, Long workerId) {
        return tasksRepository.findAllTaskBySprintAndWorker(sprintId, workerId);
    }

    
    @Transactional
    @Override
    public void createTask(Person currentPerson, Task task, Long projectId, Long personId) throws NoPermissionException {
        projectPersonService.checkPermission(Set.of(Role.ROLE_ADMIN, Role.ROLE_SUPERVISOR, Role.ROLE_STUDENT), projectId, personId);
        if (task != null) {
            task.setAuthor(currentPerson);
            task.setCreateDate(LocalDate.now());
            tasksRepository.save(task);
            log.info("Task created with author {}", currentPerson.getId());
        }
        log.error("Task cannot be null");
        throw new IllegalArgumentException("Task cannot be null");
    }

    @Transactional
    @Override
    public void updateTask(Long taskId, UpdateTaskDTO updatedTask, Long projectId, Long personId) throws NoPermissionException {
        projectPersonService.checkPermission(Set.of(Role.ROLE_ADMIN, Role.ROLE_SUPERVISOR, Role.ROLE_STUDENT), projectId, personId);
        Task task = findTaskById(taskId);

        task.setTitle(updatedTask.title());
        task.setDescription(updatedTask.description());
        task.setPriority(updatedTask.priority());
        task.setDeadline(updatedTask.deadline());
        task.setStatus(updatedTask.status());
        task.setTtz(updatedTask.ttz());
        task.setReview(updatedTask.review());
        task.setWorker(updatedTask.worker());
        task.setSprint(updatedTask.sprint());

        tasksRepository.save(task);
        log.info("Task with id {} updated", taskId);
    }

    @Transactional
    @Override
    public void deleteTask(Long taskId, Long projectId, Long personId) throws NoPermissionException {
        projectPersonService.checkPermission(Set.of(Role.ROLE_ADMIN, Role.ROLE_SUPERVISOR), projectId, personId);
        tasksRepository.delete(findTaskById(taskId));
        log.info("task {} deleted", taskId);
    }

    @Transactional
    @Override
    public void reviewTask(Long taskId, String comment, Long projectId, Long personId) throws NoPermissionException {
        projectPersonService.checkPermission(Set.of(Role.ROLE_TUTOR), projectId, personId);
        Task task = findTaskById(taskId);
        task.setReview(comment);
        task.setStatus(Status.REVIEW);
        log.info("task {} reviewed", taskId);

    }

    @Transactional
    @Override
    public void changeStatus(Long taskId, Status status, Long projectId, Long personId) {
        Task task = findTaskById(taskId);
        task.setStatus(status);
        log.info("Status is changed in task {}", taskId);
    }
}
