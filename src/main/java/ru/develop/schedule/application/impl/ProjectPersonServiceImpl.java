package ru.develop.schedule.application.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.develop.schedule.application.services.ProjectPersonService;
import ru.develop.schedule.domain.ProjectPerson;
import ru.develop.schedule.domain.ProjectPersonId;
import ru.develop.schedule.domain.enums.Role;
import ru.develop.schedule.extern.exceptions.NoPermissionException;
import ru.develop.schedule.extern.repositories.ProjectPersonRepository;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectPersonServiceImpl implements ProjectPersonService {

    private final ProjectPersonRepository projectPersonRepository;

    public void checkPermission(Set<Role> roles, Long personId, Long projectId) throws NoPermissionException {
        ProjectPerson projectPerson = projectPersonRepository
                .findById(new ProjectPersonId(projectId, personId))
                .orElseThrow(() -> new NullPointerException("No person with id " + personId + "in project id " + personId));

        boolean flag = false;
        for (Role role : roles){
            if (projectPerson.getRole().equals(role)) flag = true;
            if (!flag)
                throw new NoPermissionException("No permission person with id " + personId + "to action in project id " + personId);
        }
    }
}
