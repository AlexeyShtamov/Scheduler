package ru.develop.schedule.domain;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import ru.develop.schedule.domain.enums.Role;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TimeZone;

/**
 * Пользоватьель сервиса
 * Может иметь одну из четырех ролей (Студент, куратор, руководитель, админ)
 */
@Entity
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
    private List<Project> project = new ArrayList<>();

    @OneToMany(mappedBy = "worker")
    private List<Task> tasks = new ArrayList<>();

    @OneToMany(mappedBy = "author")
    private List<Task> authorTasks = new ArrayList<>();

    public Person(Long id, String firstName, String lastName, String email, TimeZone timeZone, String description, String phoneNumber, String telegram, String vk, String password, Role role, List<Project> project, List<Task> tasks, List<Task> authorTasks) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.timeZone = timeZone;
        this.description = description;
        this.phoneNumber = phoneNumber;
        this.telegram = telegram;
        this.vk = vk;
        this.password = password;
        this.role = role;
        this.project = project;
        this.tasks = tasks;
        this.authorTasks = authorTasks;
    }

    public Person() {
    }

    public static PersonBuilder builder() {
        return new PersonBuilder();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList(String.valueOf(this.role));
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    public Long getId() {
        return this.id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public TimeZone getTimeZone() {
        return this.timeZone;
    }

    public String getDescription() {
        return this.description;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public String getTelegram() {
        return this.telegram;
    }

    public String getVk() {
        return this.vk;
    }

    public String getPassword() {
        return this.password;
    }

    public Role getRole() {
        return this.role;
    }

    public List<Project> getProject() {
        return this.project;
    }

    public List<Task> getTasks() {
        return this.tasks;
    }

    public List<Task> getAuthorTasks() {
        return this.authorTasks;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setTelegram(String telegram) {
        this.telegram = telegram;
    }

    public void setVk(String vk) {
        this.vk = vk;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setProject(List<Project> project) {
        this.project = project;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public void setAuthorTasks(List<Task> authorTasks) {
        this.authorTasks = authorTasks;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Person)) return false;
        final Person other = (Person) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$firstName = this.getFirstName();
        final Object other$firstName = other.getFirstName();
        if (this$firstName == null ? other$firstName != null : !this$firstName.equals(other$firstName)) return false;
        final Object this$lastName = this.getLastName();
        final Object other$lastName = other.getLastName();
        if (this$lastName == null ? other$lastName != null : !this$lastName.equals(other$lastName)) return false;
        final Object this$email = this.getEmail();
        final Object other$email = other.getEmail();
        if (this$email == null ? other$email != null : !this$email.equals(other$email)) return false;
        final Object this$timeZone = this.getTimeZone();
        final Object other$timeZone = other.getTimeZone();
        if (this$timeZone == null ? other$timeZone != null : !this$timeZone.equals(other$timeZone)) return false;
        final Object this$description = this.getDescription();
        final Object other$description = other.getDescription();
        if (this$description == null ? other$description != null : !this$description.equals(other$description))
            return false;
        final Object this$phoneNumber = this.getPhoneNumber();
        final Object other$phoneNumber = other.getPhoneNumber();
        if (this$phoneNumber == null ? other$phoneNumber != null : !this$phoneNumber.equals(other$phoneNumber))
            return false;
        final Object this$telegram = this.getTelegram();
        final Object other$telegram = other.getTelegram();
        if (this$telegram == null ? other$telegram != null : !this$telegram.equals(other$telegram)) return false;
        final Object this$vk = this.getVk();
        final Object other$vk = other.getVk();
        if (this$vk == null ? other$vk != null : !this$vk.equals(other$vk)) return false;
        final Object this$password = this.getPassword();
        final Object other$password = other.getPassword();
        if (this$password == null ? other$password != null : !this$password.equals(other$password)) return false;
        final Object this$role = this.getRole();
        final Object other$role = other.getRole();
        if (this$role == null ? other$role != null : !this$role.equals(other$role)) return false;
        final Object this$project = this.getProject();
        final Object other$project = other.getProject();
        if (this$project == null ? other$project != null : !this$project.equals(other$project)) return false;
        final Object this$tasks = this.getTasks();
        final Object other$tasks = other.getTasks();
        if (this$tasks == null ? other$tasks != null : !this$tasks.equals(other$tasks)) return false;
        final Object this$authorTasks = this.getAuthorTasks();
        final Object other$authorTasks = other.getAuthorTasks();
        if (this$authorTasks == null ? other$authorTasks != null : !this$authorTasks.equals(other$authorTasks))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Person;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $firstName = this.getFirstName();
        result = result * PRIME + ($firstName == null ? 43 : $firstName.hashCode());
        final Object $lastName = this.getLastName();
        result = result * PRIME + ($lastName == null ? 43 : $lastName.hashCode());
        final Object $email = this.getEmail();
        result = result * PRIME + ($email == null ? 43 : $email.hashCode());
        final Object $timeZone = this.getTimeZone();
        result = result * PRIME + ($timeZone == null ? 43 : $timeZone.hashCode());
        final Object $description = this.getDescription();
        result = result * PRIME + ($description == null ? 43 : $description.hashCode());
        final Object $phoneNumber = this.getPhoneNumber();
        result = result * PRIME + ($phoneNumber == null ? 43 : $phoneNumber.hashCode());
        final Object $telegram = this.getTelegram();
        result = result * PRIME + ($telegram == null ? 43 : $telegram.hashCode());
        final Object $vk = this.getVk();
        result = result * PRIME + ($vk == null ? 43 : $vk.hashCode());
        final Object $password = this.getPassword();
        result = result * PRIME + ($password == null ? 43 : $password.hashCode());
        final Object $role = this.getRole();
        result = result * PRIME + ($role == null ? 43 : $role.hashCode());
        final Object $project = this.getProject();
        result = result * PRIME + ($project == null ? 43 : $project.hashCode());
        final Object $tasks = this.getTasks();
        result = result * PRIME + ($tasks == null ? 43 : $tasks.hashCode());
        final Object $authorTasks = this.getAuthorTasks();
        result = result * PRIME + ($authorTasks == null ? 43 : $authorTasks.hashCode());
        return result;
    }

    public String toString() {
        return "Person(id=" + this.getId() + ", firstName=" + this.getFirstName() + ", lastName=" + this.getLastName() + ", email=" + this.getEmail() + ", timeZone=" + this.getTimeZone() + ", description=" + this.getDescription() + ", phoneNumber=" + this.getPhoneNumber() + ", telegram=" + this.getTelegram() + ", vk=" + this.getVk() + ", password=" + this.getPassword() + ", role=" + this.getRole() + ", project=" + this.getProject() + ", tasks=" + this.getTasks() + ", authorTasks=" + this.getAuthorTasks() + ")";
    }

    public static class PersonBuilder {
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
        private Role role;
        private List<Project> project;
        private List<Task> tasks;
        private List<Task> authorTasks;

        PersonBuilder() {
        }

        public PersonBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public PersonBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public PersonBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public PersonBuilder email(String email) {
            this.email = email;
            return this;
        }

        public PersonBuilder timeZone(TimeZone timeZone) {
            this.timeZone = timeZone;
            return this;
        }

        public PersonBuilder description(String description) {
            this.description = description;
            return this;
        }

        public PersonBuilder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public PersonBuilder telegram(String telegram) {
            this.telegram = telegram;
            return this;
        }

        public PersonBuilder vk(String vk) {
            this.vk = vk;
            return this;
        }

        public PersonBuilder password(String password) {
            this.password = password;
            return this;
        }

        public PersonBuilder role(Role role) {
            this.role = role;
            return this;
        }

        public PersonBuilder project(List<Project> project) {
            this.project = project;
            return this;
        }

        public PersonBuilder tasks(List<Task> tasks) {
            this.tasks = tasks;
            return this;
        }

        public PersonBuilder authorTasks(List<Task> authorTasks) {
            this.authorTasks = authorTasks;
            return this;
        }

        public Person build() {
            return new Person(this.id, this.firstName, this.lastName, this.email, this.timeZone, this.description, this.phoneNumber, this.telegram, this.vk, this.password, this.role, this.project, this.tasks, this.authorTasks);
        }

        public String toString() {
            return "Person.PersonBuilder(id=" + this.id + ", firstName=" + this.firstName + ", lastName=" + this.lastName + ", email=" + this.email + ", timeZone=" + this.timeZone + ", description=" + this.description + ", phoneNumber=" + this.phoneNumber + ", telegram=" + this.telegram + ", vk=" + this.vk + ", password=" + this.password + ", role=" + this.role + ", project=" + this.project + ", tasks=" + this.tasks + ", authorTasks=" + this.authorTasks + ")";
        }
    }
}
