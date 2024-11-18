package ru.develop.schedule.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.develop.schedule.domain.Task;

import java.util.List;

public interface TasksRepository extends JpaRepository<Task, Long> {

    List<Task> findAllTaskBySprintAndWorker(Long sprintUuid, Long workerUuid);
}
