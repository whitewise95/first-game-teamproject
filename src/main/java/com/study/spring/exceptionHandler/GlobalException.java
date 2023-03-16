package com.study.spring.exceptionHandler;

import com.study.spring.exceptionHandler.CustumException.CustomException;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CustomException> customException(CustomException e) {
        CustomException response = new CustomException(e.getMessage(), e.getStatusCode());
        return new ResponseEntity<>(response, HttpStatus.valueOf(e.getRealStatusCode()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomException> validException(MethodArgumentNotValidException e) {
        CustomException response = new CustomException(e.getBindingResult().getAllErrors().get(0).getDefaultMessage(), 4000);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
