package ru.develop.schedule.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.develop.schedule.domain.Sprint;

public interface SprintRepository extends JpaRepository<Sprint, Long> {
}
