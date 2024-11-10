package ru.develop.schedule.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Проект (или же доска проекта), имеющий список участников-команду и задачи
 */
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
     * участники
     */
    @OneToMany(mappedBy = "project")
    private List<Person> people;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private List<Sprint> sprint = new ArrayList<>();

    public void setPeople(List<Person> people) {
        this.people.addAll(people);
    }

    public void setSprint(List<Sprint> sprint) {
        this.sprint.addAll(sprint);
    }
}
