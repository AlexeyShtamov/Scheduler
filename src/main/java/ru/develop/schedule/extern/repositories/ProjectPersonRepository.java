package ru.develop.schedule.extern.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.develop.schedule.domain.ProjectPerson;
import ru.develop.schedule.domain.ProjectPersonId;

import java.util.Optional;

@Repository
public interface ProjectPersonRepository extends JpaRepository<ProjectPerson, ProjectPersonId> {

}
