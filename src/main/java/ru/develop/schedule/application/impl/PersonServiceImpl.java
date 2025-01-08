package ru.develop.schedule.application.impl;

import ch.qos.logback.core.util.StringUtil;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.develop.schedule.application.services.PersonService;
import ru.develop.schedule.domain.Person;
import ru.develop.schedule.domain.ProjectPerson;
import ru.develop.schedule.domain.ProjectPersonId;
import ru.develop.schedule.domain.enums.Role;
import ru.develop.schedule.extern.dto.JwtRequest;
import ru.develop.schedule.extern.dto.JwtResponse;
import ru.develop.schedule.extern.exceptions.IncorrectPasswordException;
import ru.develop.schedule.extern.exceptions.PasswordMismatchException;
import ru.develop.schedule.extern.exceptions.PersonIsAlreadyExist;
import ru.develop.schedule.extern.repositories.PersonRepository;
import ru.develop.schedule.extern.repositories.ProjectPersonRepository;
import ru.develop.schedule.extern.utils.JwtTokenUtils;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PersonServiceImpl implements UserDetailsService, PersonService {

    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProjectPersonRepository projectPersonRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtils jwtTokenUtils;
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.sender.email}")
    private String senderMail;

    public PersonServiceImpl(PersonRepository personRepository,
                             @Lazy PasswordEncoder passwordEncoder,
                             ProjectPersonRepository projectPersonRepository, @Lazy AuthenticationManager authenticationManager, JwtTokenUtils jwtTokenUtils, JavaMailSender javaMailSender) {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
        this.projectPersonRepository = projectPersonRepository;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtils = jwtTokenUtils;
        this.javaMailSender = javaMailSender;
    }

    @Transactional
    public Person save(Person person, String repeatPassword) throws PersonIsAlreadyExist, PasswordMismatchException {

        if (personRepository.findByEmail(person.getEmail()).isPresent()){
            throw new PersonIsAlreadyExist("Person with email " + person.getEmail() + " is already exist");
        }
        if (!person.getPassword().equals(repeatPassword)){
            throw new PasswordMismatchException("Passwords don't match");
        }

        person.setPassword(passwordEncoder.encode(person.getPassword()));
        Person createdPerson = personRepository.save(person);
        log.info("{} {}: Person is saved", person.getFirstName(), person.getLastName());
        return createdPerson;
    }

    @Transactional
    public Person updateProfile(Long id, Person updatePerson){

        Person person = personRepository.findById(id)
                .orElseThrow(() ->
                        new NullPointerException("No person with id " + id));

        if (updatePerson.getFirstName() != null && !updatePerson.getFirstName().equals(person.getFirstName())){
            person.setFirstName(updatePerson.getFirstName());
        }
        if (updatePerson.getLastName() != null && !updatePerson.getLastName().equals(person.getLastName())){
            person.setLastName(updatePerson.getLastName());
        }
        if (updatePerson.getEmail() != null && !updatePerson.getEmail().equals(person.getEmail())){
            person.setEmail(updatePerson.getEmail());
        }
        if (updatePerson.getTimeZone() != null && !updatePerson.getTimeZone().equals(person.getTimeZone())){
            person.setTimeZone(updatePerson.getTimeZone());
        }
        if (!updatePerson.getDescription().equals(person.getDescription())){
            person.setDescription(updatePerson.getDescription());
        }

        Person updatedPerson = personRepository.save(person);
        log.info("{} {}: Person's profile is updated", person.getFirstName(), person.getLastName());
        return updatedPerson;
    }

    @Transactional
    public Person updateContacts(Long id, Person updatePerson){

        Person person = personRepository.findById(id)
                .orElseThrow(() ->
                        new NullPointerException("No person with id " + id));

        if (!updatePerson.getPhoneNumber().equals(person.getFirstName())){
            person.setPhoneNumber(updatePerson.getPhoneNumber());
        }
        if (!updatePerson.getTelegram().equals(person.getTelegram())){
            person.setTelegram(updatePerson.getTelegram());
        }
        if (!updatePerson.getVk().equals(person.getVk())){
            person.setVk(updatePerson.getVk());
        }

        Person updatedPerson = personRepository.save(person);
        log.info("{} {}: Person's contacts is updated", person.getFirstName(), person.getLastName());
        return updatedPerson;
    }

    @Transactional
    public Person updatePassword(Long id, String password, String repeatPassword) throws IncorrectPasswordException {

        Person person = personRepository.findById(id)
                .orElseThrow(() ->
                        new NullPointerException("No person with id " + id));

        if (!StringUtil.isNullOrEmpty(password) && password.equals(repeatPassword)) {
            person.setPassword(passwordEncoder.encode(password));
            person = personRepository.save(person);
        }
        else throw new IncorrectPasswordException("Your password is null or not match");
        log.info("{} {}: Person's password is updated", person.getFirstName(), person.getLastName());
        return person;
    }

    @Transactional
    public void delete(Person person){
        personRepository.delete(person);
        log.info("{} {}: Person is deleted", person.getFirstName(), person.getLastName());
    }

    @Transactional
    public void changeRole(Long projectId, Long personId, String role){
        ProjectPerson projectPerson = projectPersonRepository.findById(new ProjectPersonId(projectId, personId))
                .orElseThrow(() -> new NullPointerException("No person with id " + personId + " in project with id " + projectId));
        projectPerson.setRole(Role.valueOf(role));
        projectPersonRepository.save(projectPerson);
        log.info("Person id {}: Person's role is changed on {} in project with id {}", personId, projectId, role);
    }

    @Override
    public Person findPersonById(Long id) {
        return personRepository.findById(id)
                .orElseThrow(() ->
                        new NullPointerException("No person with id " + id));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<Person> optionalPerson = personRepository.findByEmail(email);
        if (optionalPerson.isPresent()){
            log.info("Person with email {} is founded", email);
            return optionalPerson.get();
        }
        log.warn("Person with email {} is not founded", email);
        throw new UsernameNotFoundException(email + " not found");
    }

    @Override
    public Person createAdmin(Person person, String email) {
        return null;
    }



    @Override
    public Person findByEmail(String email) {
        Person person = personRepository.findByEmail(email).orElseThrow(() -> new NullPointerException("No person with email " + email));
        log.info("Person with email {} is found", email);
        return person;
    }

    @Override
    public JwtResponse createAuthToken(JwtRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.email(), authRequest.password()));
        }catch (BadCredentialsException e){
            throw new BadCredentialsException("Неправильный логин или пароль");
        }
        UserDetails userDetails = loadUserByUsername(authRequest.email());
        String token = jwtTokenUtils.generateToken(userDetails);

        Long id = findByEmail(userDetails.getUsername()).getId();

        return new JwtResponse(id, token);
    }

    @Override
    public List<Person> findAllById(List<Long> id) {
        return personRepository.findAllById(id);
    }

    private void sendMail(String email, String password) {
        SimpleMailMessage smm = new SimpleMailMessage();

        smm.setFrom(senderMail);
        smm.setTo(email);
        smm.setSubject("Вы новый админ сервиса Scheduler!");
        smm.setText("Логин: " + email + ". Пароль: " + password);
        javaMailSender.send(smm);
    }
}
