package ru.develop.schedule.application.runners;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.develop.schedule.domain.Person;
import ru.develop.schedule.domain.enums.Role;
import ru.develop.schedule.extern.repositories.PersonRepository;

@Component
@RequiredArgsConstructor
@Slf4j
public class AdminInitializer implements CommandLineRunner {

    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin.email}")
    private String adminEmail;
    @Value("${admin.password}")
    private String adminPassword;


    @Override
    public void run(String... args) {
        if (personRepository.count() <= 3){
            Person person = new Person();

            person.setEmail(adminEmail);
            person.setPassword(passwordEncoder.encode(adminPassword));
            person.setFirstName("AdminFirstName");
            person.setLastName("AdminLastName");
            person.setRole(Role.ROLE_ADMIN);
            personRepository.save(person);
            log.info("AdminInitializer: First admin in application is created");
        }
    }
}
