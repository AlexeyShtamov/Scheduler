package ru.develop.schedule.application.impl;

import ch.qos.logback.core.util.StringUtil;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import ru.develop.schedule.application.services.PersonService;
import ru.develop.schedule.application.services.ProjectPersonService;
import ru.develop.schedule.application.services.ProjectService;
import ru.develop.schedule.domain.Person;
import ru.develop.schedule.domain.Project;
import ru.develop.schedule.domain.ProjectPerson;
import ru.develop.schedule.domain.ProjectPersonId;
import ru.develop.schedule.domain.enums.Role;
import ru.develop.schedule.extern.exceptions.NoPermissionException;
import ru.develop.schedule.extern.repositories.ProjectPersonRepository;

import java.util.Set;

@Service
public class ProjectPersonServiceImpl implements ProjectPersonService {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(ProjectPersonServiceImpl.class);
    private final ProjectPersonRepository projectPersonRepository;

    private final PersonService personService;
    private final ProjectService projectService;

    public ProjectPersonServiceImpl(ProjectPersonRepository projectPersonRepository, PersonService personService, ProjectService projectService) {
        this.projectPersonRepository = projectPersonRepository;
        this.personService = personService;
        this.projectService = projectService;
    }

    public void checkPermission(Set<Role> roles, Long personId, Long projectId) throws NoPermissionException {
        Person person = personService.findPersonById(personId);
        if (person.getRole() != null && person.getRole().equals(Role.ROLE_ADMIN)) return;

        ProjectPerson projectPerson = projectPersonRepository
                .findById(new ProjectPersonId(projectId, personId))
                .orElseThrow(() -> new NullPointerException("No person with id " + personId + " in project id " + personId));

        boolean flag = false;
        for (Role role : roles) {
            if (projectPerson.getRole().equals(role)) {
                flag = true;
                break;
            }
        }
        if (!flag) {
            throw new NoPermissionException("No permission person with id " + personId + " to action in project id " + personId);
        }

    }

    @Override
    public void addSpecialPerson(Long userId, Long projectId, Person person, String strRole) {
        Person mainPerson = personService.findPersonById(userId);

        if (!mainPerson.getRole().equals(Role.ROLE_ADMIN)) {
            ProjectPerson projectPerson = projectPersonRepository.findById(new ProjectPersonId(projectId, userId)).orElseThrow(() -> new NullPointerException("You have no permission for adding"));
            if (!projectPerson.getRole().equals(Role.ROLE_SUPERVISOR))
                throw new NullPointerException("You have no permission for adding");
        }


        ProjectPersonId id = new ProjectPersonId(projectId, person.getId());
        Project project = projectService.findProjectById(projectId);

        Role role = null;
        if (!StringUtil.isNullOrEmpty(strRole) &&
                (Role.valueOf(strRole).equals(Role.ROLE_SUPERVISOR)
                        || Role.valueOf(strRole).equals(Role.ROLE_TUTOR))
                || Role.valueOf(strRole).equals(Role.ROLE_STUDENT)) {
            role = Role.valueOf(strRole);
        }

        projectPersonRepository.save(new ProjectPerson(id, project, person, role));
    }
}
