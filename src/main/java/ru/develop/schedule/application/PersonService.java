package ru.develop.schedule.application;

import ru.develop.schedule.domain.Person;
import ru.develop.schedule.domain.Project;
import ru.develop.schedule.extern.exceptions.PasswordMismatchException;
import ru.develop.schedule.extern.exceptions.PersonIsAlreadyExist;

public interface PersonService {
    Person save(Person person, String repeatPassword) throws PersonIsAlreadyExist, PasswordMismatchException;

    Person updateProfile(Person optionalPerson, Person updatePerson);

    Person updateContacts(Person person, Person updatePerson);

    Person updatePassword(Person person, String password);

    void delete(Person person);

    void changeRole(Long projectId, Long personId, String role) throws Exception;

}
