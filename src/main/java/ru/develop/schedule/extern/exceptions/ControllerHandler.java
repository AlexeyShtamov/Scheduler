package ru.develop.schedule.extern.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ControllerHandler {

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<String> usernameNotFoundException(UsernameNotFoundException e){
        log.error(e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> nullPointerException(NullPointerException e){
        log.error(e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PersonIsAlreadyExist.class)
    public ResponseEntity<String> personIsAlreadyExist(PersonIsAlreadyExist e){
        log.error(e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<String> personIsAlreadyExist(PasswordMismatchException e){
        log.error(e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
