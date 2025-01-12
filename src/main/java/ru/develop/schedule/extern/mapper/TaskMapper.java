package ru.develop.schedule.extern.mapper;

import org.springframework.stereotype.Component;
import ru.develop.schedule.application.services.PersonService;
import ru.develop.schedule.application.services.SprintService;
import ru.develop.schedule.domain.Task;
import ru.develop.schedule.extern.dto.TaskDTO;

@Component
public class TaskMapper {

    private final PersonService personService;
    private final SprintService sprintService;
    private final SprintMapper sprintMapper;

    public TaskMapper(PersonService personService, SprintService sprintService, SprintMapper sprintMapper) {
        this.personService = personService;
        this.sprintService = sprintService;
        this.sprintMapper = sprintMapper;
    }

    public TaskDTO getTaskDTOFromTask(Task task) {
        return new TaskDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getPriority(),
                task.getAssignDate(),
                task.getDeadline(),
                task.getWorker().getFirstName() + " " + task.getWorker().getLastName(),
                task.getAuthor().getFirstName() + " " + task.getAuthor().getLastName(),
                task.getTtz(),
                sprintMapper.getSprintDTOFromSprint(task.getSprint()),
                task.getStatus(),
                task.getReview()
        );
    }

    public Task getTaskFromTaskDTO(TaskDTO taskDTO) {

        return Task.builder()
                .id(taskDTO.id())
                .author(personService.findByFullName(taskDTO.author().split(" ")[0], taskDTO.author().split(" ")[1]))
                .title(taskDTO.title())
                .description(taskDTO.description())
                .priority(taskDTO.priority())
                .ttz(taskDTO.ttz())
                .sprint(sprintService.findSprintById(taskDTO.sprint().id()))
                .status(taskDTO.status())
                .deadline(taskDTO.endDate())
                .assignDate(taskDTO.startDate())
                .review(taskDTO.review())
                .worker(personService.findByFullName(taskDTO.assignee().split(" ")[0], taskDTO.assignee().split(" ")[1]))
                .build();
    }
}
