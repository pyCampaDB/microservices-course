package com.msvc.user.user_service.exceptions;
import com.msvc.user.user_service.model.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
@RestControllerAdvice //Clase que va a controlar excepciones
public class GlobalExceptionController {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<APIResponse> handlerResourceNotFoundException(
            ResourceNotFoundException resourceNotFoundException
    ) {
        String message = resourceNotFoundException.getMessage();
        APIResponse response = new APIResponse().builder()
                .message(message)
                .success(true)
                .status(HttpStatus.NOT_FOUND)
                .build();
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }
}
