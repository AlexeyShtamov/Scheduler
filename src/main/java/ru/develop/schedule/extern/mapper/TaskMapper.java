package ru.develop.schedule.extern.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.develop.schedule.application.services.PersonService;
import ru.develop.schedule.application.services.SprintService;
import ru.develop.schedule.domain.Task;
import ru.develop.schedule.extern.dto.TaskPersonDto;
import ru.develop.schedule.extern.dto.TaskDTO;

@Component
@RequiredArgsConstructor
public class TaskMapper {

    private final PersonService personService;
    private final SprintService sprintService;
    private final SprintMapper sprintMapper;

    public TaskDTO getTaskDTOFromTask(Task task) {
        return new TaskDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getPriority(),
                task.getAssignDate(),
                task.getDeadline(),
                new TaskPersonDto(task.getWorker().getId(), task.getWorker().getFirstName(), task.getWorker().getLastName()),
                new TaskPersonDto(task.getAuthor().getId(), task.getAuthor().getFirstName(), task.getAuthor().getLastName()),
                task.getTtz(),
                sprintMapper.getSprintDTOFromSprint(task.getSprint()),
                task.getStatus(),
                task.getReview()
        );
    }

    public Task getTaskFromTaskDTO(TaskDTO taskDTO) {

        return Task.builder()
                .id(taskDTO.id())
                .author(personService.findPersonById(taskDTO.author().id()))
                .title(taskDTO.title())
                .description(taskDTO.description())
                .priority(taskDTO.priority())
                .ttz(taskDTO.ttz())
                .sprint(sprintService.findSprintById(taskDTO.sprint().id()))
                .status(taskDTO.status())
                .deadline(taskDTO.endDate())
                .assignDate(taskDTO.startDate())
                .review(taskDTO.review())
                .worker(personService.findPersonById(taskDTO.assignee().id()))
                .build();
    }
}
