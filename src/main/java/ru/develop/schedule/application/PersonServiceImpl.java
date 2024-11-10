package ru.develop.schedule.application;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.develop.schedule.domain.Person;
import ru.develop.schedule.domain.Project;
import ru.develop.schedule.domain.ProjectPerson;
import ru.develop.schedule.domain.ProjectPersonId;
import ru.develop.schedule.domain.enums.Role;
import ru.develop.schedule.extern.exceptions.PasswordMismatchException;
import ru.develop.schedule.extern.exceptions.PersonIsAlreadyExist;
import ru.develop.schedule.extern.exceptions.PersonNotFoundException;
import ru.develop.schedule.extern.repositories.PersonRepository;
import ru.develop.schedule.extern.repositories.ProjectPersonRepository;

import java.util.Optional;

@Service
@Slf4j
public class PersonServiceImpl implements UserDetailsService, PersonService {

    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProjectPersonRepository projectPersonRepository;

    public PersonServiceImpl(PersonRepository personRepository, PasswordEncoder passwordEncoder, ProjectPersonRepository projectPersonRepository) {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
        this.projectPersonRepository = projectPersonRepository;
    }

    public Person save(Person person, String repeatPassword) throws PersonIsAlreadyExist, PasswordMismatchException {

        if (personRepository.findByEmail(person.getEmail()).isPresent()){
            throw new PersonIsAlreadyExist("Person with email " + person.getEmail() + " is already exist");
        }
        if (!person.getPassword().equals(repeatPassword)){
            throw new PasswordMismatchException("Passwords don't match");
        }

        person.setPassword(passwordEncoder.encode(person.getPassword()));
        Person createdPerson = personRepository.save(person);
        log.info("{} {}: Person is saved", person.getFirstName(), person.getLastName());
        return createdPerson;
    }

    public Person updateProfile(Person person, Person updatePerson){

        if (updatePerson.getFirstName() != null && !updatePerson.getFirstName().equals(person.getFirstName())){
            person.setFirstName(updatePerson.getFirstName());
        }
        if (updatePerson.getLastName() != null && !updatePerson.getLastName().equals(person.getLastName())){
            person.setLastName(updatePerson.getLastName());
        }
        if (updatePerson.getEmail() != null && !updatePerson.getEmail().equals(person.getEmail())){
            person.setEmail(updatePerson.getEmail());
        }
        if (updatePerson.getTimeZone() != null && !updatePerson.getTimeZone().equals(person.getTimeZone())){
            person.setTimeZone(updatePerson.getTimeZone());
        }
        if (!updatePerson.getDescription().equals(person.getDescription())){
            person.setDescription(updatePerson.getDescription());
        }

        Person updatedPerson = personRepository.save(person);
        log.info("{} {}: Person's profile is updated", person.getFirstName(), person.getLastName());
        return updatedPerson;
    }

    public Person updateContacts(Person person, Person updatePerson){

        if (!updatePerson.getPhoneNumber().equals(person.getFirstName())){
            person.setPhoneNumber(updatePerson.getPhoneNumber());
        }
        if (!updatePerson.getTelegram().equals(person.getTelegram())){
            person.setTelegram(updatePerson.getTelegram());
        }
        if (!updatePerson.getVk().equals(person.getVk())){
            person.setVk(updatePerson.getVk());
        }

        Person updatedPerson = personRepository.save(person);
        log.info("{} {}: Person's contacts is updated", person.getFirstName(), person.getLastName());
        return updatedPerson;
    }


    public Person updatePassword(Person person, String password){

        if (password != null) {
            person.setPassword(passwordEncoder.encode(password));
            person = personRepository.save(person);
        }
        log.info("{} {}: Person's password is updated", person.getFirstName(), person.getLastName());
        return person;
    }

    public void delete(Person person){

        personRepository.delete(person);
        log.info("{} {}: Person is deleted", person.getFirstName(), person.getLastName());
    }


    public void changeRole(Long projectId, Long personId, String role) throws Exception {
        ProjectPerson projectPerson = projectPersonRepository.findById(new ProjectPersonId(projectId, personId)).orElseThrow(Exception::new);
        projectPerson.setRole(Role.valueOf(role));
        projectPersonRepository.save(projectPerson);
        log.info("Person id {}: Person's role is changed on {} in project with id {}", personId, projectId, role);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<Person> optionalPerson = personRepository.findByEmail(email);
        if (optionalPerson.isPresent()){
            log.info("Person with email {} is founded", email);
            return optionalPerson.get();
        }
        log.warn("Person with email {} is not founded", email);
        throw new UsernameNotFoundException(email + " not found");
    }
}
