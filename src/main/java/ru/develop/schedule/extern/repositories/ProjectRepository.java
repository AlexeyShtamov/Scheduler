package ru.develop.schedule.extern.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.develop.schedule.domain.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
