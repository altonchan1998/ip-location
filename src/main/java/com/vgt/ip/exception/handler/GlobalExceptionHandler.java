package com.vgt.ip.exception.handler;

import com.vgt.ip.constant.ResponseConstants;
import com.vgt.ip.exception.IPLocationNotFoundException;
import com.vgt.ip.domain.applicationservice.dto.Result;
import io.micrometer.core.instrument.config.validate.ValidationException;
import jakarta.validation.ConstraintViolation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import jakarta.validation.ConstraintViolationException;


import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<Void> handleException(Exception exception) {
//        log.error(exception.getMessage(), exception);
        return new Result<>(ResponseConstants.RESPONSE_CODE_ERROR, null, "Unexpected error");
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<Void> handleException(ValidationException validationException) {
        String exceptionMessage = validationException.getMessage();
        log.error(exceptionMessage, validationException);
        return new Result<>(ResponseConstants.RESPONSE_CODE_ERROR, null, exceptionMessage);
    }

    @ExceptionHandler(IPLocationNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Result<Void> handleException(IPLocationNotFoundException ipLocationNotFoundException) {
        String exceptionMessage = "IP not found: " + ipLocationNotFoundException.getMessage();
        log.error(exceptionMessage, ipLocationNotFoundException);
        return new Result<>(ResponseConstants.RESPONSE_CODE_ERROR, null, exceptionMessage);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<Void> handleException(ConstraintViolationException constraintViolationException) {
        String violations = extractViolationsFromException(constraintViolationException);
        log.error(violations, constraintViolationException);
        return new Result<>(ResponseConstants.RESPONSE_CODE_ERROR, null, violations);
    }

    private String extractViolationsFromException(ConstraintViolationException constraintViolationException) {
        return constraintViolationException.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(","));
    }
}
