package biblioteca.spring.controller;

import biblioteca.spring.exception.book.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class LibraryControllerAdvice {

    @ExceptionHandler(BookNullException.class)
    public ResponseEntity<Object> handleBookNullException(BookNullException ex){
        return buildResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BookNoIDException.class)
    public ResponseEntity<Object> bookNoIDException(BookNoIDException ex){
        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BookListNullException.class)
    public ResponseEntity<Object> bookListNullException(BookListNullException ex){
        return buildResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BookRentTrueException.class)
    public ResponseEntity<Object> bookRentTrueException(BookRentTrueException ex){
        return buildResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Object> buildResponse(String message, HttpStatus status) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", message);
        return ResponseEntity.status(status).body(body);
    }
}
