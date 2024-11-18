package ru.develop.schedule.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.develop.schedule.domain.enums.Role;

import java.util.List;
import java.util.Map;

/**
 * Проект (или же доска проекта), имеющий список участников-команду и задачи
 * */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String boardName;

    /**
     *  участники
     */
    @ManyToMany
    private List<Person> people;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private List<Sprint> sprint;
}