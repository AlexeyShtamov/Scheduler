package ru.develop.schedule.application.runners;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.develop.schedule.domain.Person;
import ru.develop.schedule.domain.enums.Role;
import ru.develop.schedule.extern.repositories.PersonRepository;

@Component
public class AdminInitializer implements CommandLineRunner {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(AdminInitializer.class);
    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin.email}")
    private String adminEmail;
    @Value("${admin.password}")
    private String adminPassword;

    public AdminInitializer(PersonRepository personRepository, PasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void run(String... args) {
        if (personRepository.count() <= 3) {
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
