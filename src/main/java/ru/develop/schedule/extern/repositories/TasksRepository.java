package ru.develop.schedule.extern.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.develop.schedule.domain.Task;

import java.util.List;

public interface TasksRepository extends JpaRepository<Task, Long> {

    List<Task> findAllTaskBySprintIdAndWorkerId(Long sprintUuid, Long workerUuid);
}
