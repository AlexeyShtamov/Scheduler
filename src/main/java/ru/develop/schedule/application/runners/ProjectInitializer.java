package ru.develop.schedule.application.runners;

import org.slf4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.develop.schedule.domain.Person;
import ru.develop.schedule.domain.Project;
import ru.develop.schedule.domain.ProjectPerson;
import ru.develop.schedule.domain.ProjectPersonId;
import ru.develop.schedule.domain.enums.Role;
import ru.develop.schedule.extern.repositories.ProjectPersonRepository;
import ru.develop.schedule.extern.repositories.ProjectRepository;

@Component
public class ProjectInitializer implements CommandLineRunner {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(ProjectInitializer.class);
    private final ProjectRepository projectRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProjectPersonRepository projectPersonRepository;

    public ProjectInitializer(ProjectRepository projectRepository, PasswordEncoder passwordEncoder, ProjectPersonRepository projectPersonRepository) {
        this.projectRepository = projectRepository;
        this.passwordEncoder = passwordEncoder;
        this.projectPersonRepository = projectPersonRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (projectRepository.count() == 0) {
            Person person1 = Person.builder()
                    .firstName("Алексей")
                    .lastName("Штамов")
                    .email("shamov0.mail.ru")
                    .password(passwordEncoder.encode("Password123"))
                    .description("Меня зовут Алексей Штамов!")
                    .build();
            Person person2 = Person.builder()
                    .firstName("Игорь")
                    .lastName("Матюгин")
                    .email("matugin1.mail.ru")
                    .password(passwordEncoder.encode("Password456"))
                    .description("Меня зовут Игорь Матюгин!")
                    .build();
            Person person3 = Person.builder()
                    .firstName("Иван")
                    .lastName("Иванов")
                    .email("ivanov.mail.ru")
                    .password(passwordEncoder.encode("Password789"))
                    .description("Меня зовут Иван Иванов!")
                    .build();

            Project project = new Project();
            project.setBoardName("Brainstorm");
            project.setPeople(person1);
            project.setPeople(person2);
            project.setPeople(person3);
            project = projectRepository.save(project);

            ProjectPerson projectPerson = new ProjectPerson(new ProjectPersonId(person1.getId(), project.getId()), project, person1, Role.ROLE_SUPERVISOR);
            ProjectPerson projectPerson1 = new ProjectPerson(new ProjectPersonId(person2.getId(), project.getId()), project, person2, Role.ROLE_TUTOR);
            ProjectPerson projectPerson2 = new ProjectPerson(new ProjectPersonId(person3.getId(), project.getId()), project, person3, Role.ROLE_STUDENT);

            projectPersonRepository.save(projectPerson);
            projectPersonRepository.save(projectPerson1);
            projectPersonRepository.save(projectPerson2);
        }
    }
}
