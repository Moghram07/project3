package com.example.project3.Advice;

import com.example.project3.ApiException.ApiException;
import jakarta.validation.UnexpectedTypeException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {
    //typically thrown when a business rule or specific validation fails.
    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity ApiException(ApiException e){
        String message = e.getMessage();
        return ResponseEntity.status(400).body(message);
    }
    //occurs when an operation attempts to violate database constraints, like unique or foreign key constraints.
    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public ResponseEntity DataIntegrityViolationException(DataIntegrityViolationException e){
        String message = e.getMessage();
        return ResponseEntity.status(400).body(message);
    }
    //thrown when the application is in an unexpected state like updating a non-existing entity
    @ExceptionHandler(value = IllegalStateException.class)
    public ResponseEntity IllegalStateException(IllegalStateException e){
        String message = e.getMessage();
        return ResponseEntity.status(400).body(message);
    }
    //thrown when a resource is not found
    @ExceptionHandler(value = NoResourceFoundException.class)
    public ResponseEntity NoResourceFoundException(NoResourceFoundException e){
        String message = e.getMessage();
        return ResponseEntity.status(404).body(message);
    }
    //thrown when a method argument is not the expected type (like using POST where only GET is allowed)
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ResponseEntity HttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e){
        String message = e.getMessage();
        return ResponseEntity.status(400).body(message);
    }
    //thrown when the request body cannot be parsed, typically due to malformed JSON or incorrect input format.
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity HttpMessageNotReadableException(HttpMessageNotReadableException e){
        String message = e.getMessage();
        return ResponseEntity.status(400).body(message);
    }
    //thrown when a method argument type does not match the expected type, often occurring in path variables or request parameters.
    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public ResponseEntity MethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e){
        String message = e.getMessage();
        return ResponseEntity.status(400).body(message);
    }
    //thrown when a method argument fails validation (like balance can't be negative)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity MethodArgumentNotValidException(MethodArgumentNotValidException e){
        String message = e.getMessage();
        return ResponseEntity.status(400).body(message);
    }
    //thrown when the request body has an unsupported media type (like typing wrong status name in ShippingStatus enum)
    @ExceptionHandler(value = HttpMediaTypeNotSupportedException.class)
    public ResponseEntity HttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e){
        String message = e.getMessage();
        return ResponseEntity.status(400).body(message);
    }

    @ExceptionHandler(value = JpaSystemException.class)
    public ResponseEntity JpaSystemException(JpaSystemException e){
        String message = e.getMessage();
        return ResponseEntity.status(400).body(message);
    }

    @ExceptionHandler(value = UnexpectedTypeException.class)
    public ResponseEntity UnexpectedTypeException(UnexpectedTypeException e){
        String message = e.getMessage();
        return ResponseEntity.status(400).body(message);
    }
}
