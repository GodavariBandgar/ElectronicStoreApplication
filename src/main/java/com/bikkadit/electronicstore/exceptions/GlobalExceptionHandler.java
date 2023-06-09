package com.bikkadit.electronicstore.exceptions;

import com.bikkadit.electronicstore.payloads.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
     Logger logger= LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex) {
       logger.info("Exception Handler invoked!!");
        ApiResponse response = ApiResponse.builder().message(ex.getMessage()).status(HttpStatus.NOT_FOUND).success(true).build();


        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }

    //method argument not valid Exception

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleMethodArgumentValidException(MethodArgumentNotValidException ex) {
        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();

        Map<String,Object> response = new HashMap<>();
        allErrors.stream().forEach((objectError) -> {
            String fieldName = ((FieldError) objectError).getField();
            String message = objectError.getDefaultMessage();
            response.put(fieldName, message);

        });

        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);


    }

    @ExceptionHandler(BadApiRequestException.class)
    public ResponseEntity<ApiResponse> handleBadApiRequest(BadApiRequestException ex) {
        logger.info("Bad Api Request!");
        ApiResponse response = ApiResponse.builder().message(ex.getMessage()).status(HttpStatus.NOT_FOUND).success(false).build();


        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }


}
