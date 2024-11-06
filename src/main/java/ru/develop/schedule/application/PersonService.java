package ru.develop.schedule.application;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.develop.schedule.domain.Person;
import ru.develop.schedule.extern.repositories.PersonRepository;

import java.util.Optional;

@Service
@Slf4j
public class PersonService implements UserDetailsService {

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
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
