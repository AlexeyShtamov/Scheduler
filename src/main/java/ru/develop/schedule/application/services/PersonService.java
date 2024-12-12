package ru.develop.schedule.application.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.develop.schedule.domain.Person;
import ru.develop.schedule.extern.dto.JwtRequest;
import ru.develop.schedule.extern.dto.JwtResponse;
import ru.develop.schedule.extern.exceptions.IncorrectPasswordException;
import ru.develop.schedule.extern.exceptions.PasswordMismatchException;
import ru.develop.schedule.extern.exceptions.PersonIsAlreadyExist;

public interface PersonService extends UserDetailsService {
    Person save(Person person, String repeatPassword) throws PersonIsAlreadyExist, PasswordMismatchException;

    Person updateProfile(Long id, Person updatePerson);

    Person updateContacts(Long id, Person updatePerson);

    Person updatePassword(Long id, String password, String repeatPassword) throws IncorrectPasswordException;

    void delete(Person person);

    void changeRole(Long projectId, Long personId, String role) throws Exception;

    Person findPersonById(Long id);

    Person createAdmin(Person person, String email) throws PersonIsAlreadyExist;

    Person findByEmail(String email);

    JwtResponse createAuthToken(JwtRequest authRequest);
}
