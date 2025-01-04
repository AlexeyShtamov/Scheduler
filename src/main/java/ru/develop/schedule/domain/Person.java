package ru.develop.schedule.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import ru.develop.schedule.domain.enums.Role;

import java.util.Collection;
import java.util.List;
import java.util.TimeZone;

/**
 * Пользоватьель сервиса
 * Может иметь одну из четырех ролей (Студент, куратор, руководитель, админ)
 * */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private TimeZone timeZone;
    private String description;

    private String phoneNumber;
    private String telegram;
    private String vk;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToMany
    private List<Project> project;

    @OneToMany(mappedBy = "worker")
    private List<Task> tasks;

    @OneToMany(mappedBy = "author")
    private List<Task> authorTasks;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList(String.valueOf(this.role));
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}
