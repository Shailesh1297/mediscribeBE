package org.mediscribe.exception;

import org.mediscribe.dto.ApiResponse;
import org.mediscribe.dto.ApiResponseMeta;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {




    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponseMeta> userNotFoundException(UserNotFoundException exception){
        return new ResponseEntity<>(new ApiResponseMeta(-1, exception.getMessage()), HttpStatus.OK);
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<ApiResponseMeta> roleNotFoundException(RoleNotFoundException exception){
        return new ResponseEntity<>(new ApiResponseMeta(-1, exception.getMessage()), HttpStatus.OK);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponseMeta> badCredentialsException(BadCredentialsException exception){
        return new ResponseEntity<>(new ApiResponseMeta(-1, exception.getMessage()), HttpStatus.OK);
    }


}
