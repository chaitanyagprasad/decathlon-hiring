package com.hiring.decathlonv1.controlleradvice;

import com.hiring.decathlonv1.errors.CustomError;
import com.hiring.decathlonv1.exceptions.BusinessLogicException;
import com.hiring.decathlonv1.exceptions.PhoneNumberNotValidException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({PhoneNumberNotValidException.class, BusinessLogicException.class})
    public ResponseEntity<Object> handleBusinessExceptions(Exception ex, WebRequest request) {

        CustomError error = new CustomError();
        error.setErrorMessage(ex.getMessage());
        error.setHttpStatus(400);

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);

    }
}
