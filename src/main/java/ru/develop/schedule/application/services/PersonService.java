package ru.develop.schedule.application.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.develop.schedule.domain.Person;
import ru.develop.schedule.extern.exceptions.PasswordMismatchException;
import ru.develop.schedule.extern.exceptions.PersonIsAlreadyExist;

public interface PersonService extends UserDetailsService {
    Person save(Person person, String repeatPassword) throws PersonIsAlreadyExist, PasswordMismatchException;

    Person updateProfile(Person optionalPerson, Person updatePerson);

    Person updateContacts(Person person, Person updatePerson);

    Person updatePassword(Person person, String password);

    void delete(Person person);

    void changeRole(Long projectId, Long personId, String role) throws Exception;

    void makeReview();

}
