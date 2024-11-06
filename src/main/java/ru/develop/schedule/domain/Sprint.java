package ru.develop.schedule.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "tbl_sprint")
public class Sprint {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    @Column(unique = false, nullable = false, length = 255)
    private String title;

    /**
     * Дата начала спринта
     */
    @Column(nullable = false)
    private Date startTime;

    /**
     * Дата конца спринта
     */
    @Column(nullable = false)
    private Date endTime;

    @OneToMany(mappedBy = "sprint", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private List<Task> tasks = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Project project;
}
