package ru.develop.schedule.extern.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.develop.schedule.domain.Person;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByEmail(String email);

    Optional<Person> findByFirstNameAndLastName(String firstName, String lastName);

}
