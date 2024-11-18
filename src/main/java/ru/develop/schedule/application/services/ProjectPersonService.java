package ru.develop.schedule.application.services;

import org.springframework.stereotype.Service;
import ru.develop.schedule.domain.enums.Role;
import ru.develop.schedule.extern.exceptions.NoPermissionException;
import ru.develop.schedule.extern.repositories.ProjectPersonRepository;

import java.util.Set;

@Service
public interface ProjectPersonService {
    void checkPermission(Set<Role> roleAdmin, Long projectId, Long personId) throws NoPermissionException;
}
