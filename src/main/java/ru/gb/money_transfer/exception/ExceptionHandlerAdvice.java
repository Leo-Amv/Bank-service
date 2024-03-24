package ru.gb.money_transfer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(IncorrectDataEntry.class)
    public ResponseEntity<String> ideHandler(IncorrectDataEntry exc) {
        exc.printStackTrace();
        return new ResponseEntity<>(exc.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataMismatch.class)
    public ResponseEntity<String> icHandler(DataMismatch exc) {
        exc.printStackTrace();
        return new ResponseEntity<>(exc.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}