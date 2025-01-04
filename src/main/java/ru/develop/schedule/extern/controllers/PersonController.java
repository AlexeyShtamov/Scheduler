package ru.develop.schedule.extern.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.develop.schedule.application.services.PersonService;
import ru.develop.schedule.domain.Person;
import ru.develop.schedule.extern.dto.*;
import ru.develop.schedule.extern.exceptions.IncorrectPasswordException;
import ru.develop.schedule.extern.exceptions.PasswordMismatchException;
import ru.develop.schedule.extern.exceptions.PersonIsAlreadyExist;
import ru.develop.schedule.extern.mapper.PersonMapper;

@RestController
@RequestMapping("/people")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;
    private final PersonMapper personMapper;

    @PostMapping
    public ResponseEntity<InfoPersonDTO> save(@RequestBody CreatePersonDTO createPersonDTO) throws PersonIsAlreadyExist, PasswordMismatchException {
        Person createdPerson = personService.save(personMapper.fromCreateDTOToPerson(createPersonDTO), createPersonDTO.repeatPassword());
        return new ResponseEntity<>(personMapper.fromPersonToDTO(createdPerson), HttpStatus.OK);
    }

    @PostMapping("/admin")
    public ResponseEntity<InfoPersonDTO> createAdmin(@RequestBody CreateAdminDTO createAdminDTO) throws PersonIsAlreadyExist, PasswordMismatchException {
        Person createdPerson = personService.createAdmin(personMapper.fromCreateAdminDTOToPerson(createAdminDTO), createAdminDTO.emailSend());
        return new ResponseEntity<>(personMapper.fromPersonToDTO(createdPerson), HttpStatus.OK);
    }

    @PutMapping("/profile/{id}")
    public ResponseEntity<InfoPersonDTO> updateProfile(@RequestBody InfoPersonDTO infoPersonDTO, @PathVariable Long id){
        Person person = personMapper.fromInfoDTOToPerson(infoPersonDTO);
        Person updatedPerson = personService.updateProfile(id, person);
        return new ResponseEntity<>(personMapper.fromPersonToDTO(updatedPerson), HttpStatus.OK);
    }

    @PutMapping("/contacts/{id}")
    public ResponseEntity<InfoPersonDTO> updateContacts(@RequestBody InfoPersonDTO infoPersonDTO, @PathVariable Long id){
        Person person = personMapper.fromInfoDTOToPerson(infoPersonDTO);
        Person updatedPerson = personService.updateContacts(id, person);
        return new ResponseEntity<>(personMapper.fromPersonToDTO(updatedPerson), HttpStatus.OK);
    }

    @PutMapping("/password/{id}")
    public ResponseEntity<InfoPersonDTO> updatePassword(@RequestBody UpdatePasswordDTO updatePasswordDTO, @PathVariable Long id) throws IncorrectPasswordException {
        Person updatedPerson = personService.updatePassword(id, updatePasswordDTO.password(), updatePasswordDTO.repeatPassword());
        return new ResponseEntity<>(personMapper.fromPersonToDTO(updatedPerson), HttpStatus.OK);
    }

    @PostMapping("/auth")
    public ResponseEntity<JwtResponse> createAuthToken(@RequestBody JwtRequest authRequest) {
        JwtResponse jwtResponse = personService.createAuthToken(authRequest);
        return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
    }




}
