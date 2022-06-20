package ru.koniaev.bookstorage.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {
    
    @ExceptionHandler(WrongInputParametersException.class)
    public ResponseEntity<ErrorDto> handleWrongInputParams(Exception e) {
        return new ResponseEntity<>(new ErrorDto(e.getLocalizedMessage()), HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler({BookNotFoundException.class, AuthorNotFoundException.class, GenreNotFoundException.class})
    public ResponseEntity<ErrorDto> handleNotFound (Exception e) {
        return new ResponseEntity<>(new ErrorDto(e.getLocalizedMessage()), HttpStatus.NOT_FOUND);
    }
}
