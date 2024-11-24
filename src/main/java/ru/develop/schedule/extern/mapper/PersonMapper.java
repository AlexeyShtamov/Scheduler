package ru.develop.schedule.extern.mapper;

import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;
import ru.develop.schedule.domain.Person;
import ru.develop.schedule.extern.dto.CreatePersonDTO;
import ru.develop.schedule.extern.dto.InfoPersonDTO;

@Component
public class PersonMapper {

    public Person fromCreateDTOToPerson(CreatePersonDTO createPersonDTO){
        Person person = new Person();

        person.setFirstName(createPersonDTO.firstName());
        person.setLastName(createPersonDTO.lastName());
        person.setPhoneNumber(createPersonDTO.phone());
        person.setEmail(createPersonDTO.email());
        person.setPassword(createPersonDTO.password());
        return person;
    }

    public Person fromInfoDTOToPerson(InfoPersonDTO infoPersonDTO){
        Person person = new Person();

        person.setId(infoPersonDTO.id());
        person.setFirstName(infoPersonDTO.firstName());
        person.setLastName(infoPersonDTO.lastName());
        person.setEmail(infoPersonDTO.email());
        person.setTimeZone(infoPersonDTO.timeZone());
        person.setDescription(infoPersonDTO.description());
        person.setPhoneNumber(infoPersonDTO.phone());
        person.setTelegram(infoPersonDTO.telegram());
        person.setVk(infoPersonDTO.vk());

        return person;
    }

    public InfoPersonDTO fromPersonToDTO(Person person){
        return new InfoPersonDTO(
                person.getId(),
                person.getFirstName(),
                person.getLastName(),
                person.getEmail(),
                person.getTimeZone(),
                person.getDescription(),
                person.getPhoneNumber(),
                person.getTelegram(),
                person.getVk()
        );
    }
}
