package ru.develop.schedule.extern.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.develop.schedule.application.PersonServiceImpl;

@RestController
@RequestMapping("/test")
public class TestController {

    private final PersonServiceImpl personServiceImpl;

    public TestController(PersonServiceImpl personServiceImpl) {
        this.personServiceImpl = personServiceImpl;
    }

    @RequestMapping("/some")
    public ResponseEntity<String> test(){
        return new ResponseEntity<>("Test", HttpStatus.OK);
    }

}
