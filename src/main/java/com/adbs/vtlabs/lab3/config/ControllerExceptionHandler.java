package com.adbs.vtlabs.lab3.config;

import com.adbs.vtlabs.lab3.exception.ServiceException;
import com.adbs.vtlabs.lab3.model.config.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static com.adbs.vtlabs.lab3.exception.ErrorCode.*;

@RestControllerAdvice
@Slf4j
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {ServiceException.class})
    public ResponseEntity<ErrorResponse> handleServiceException(ServiceException e) {
        log.error(e.getErrorResponse().getMessage().getLangRu(), e);
        return ResponseEntity.status(e.getErrorResponse().getCode())
                .body(e.getErrorResponse());
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error("Endpoint не найден", ex);
        ErrorResponse errorResponse = ErrorResponse.fromErrorStatus(ENDPOINT_NOT_FOUND);
        return ResponseEntity.status(errorResponse.getCode())
                .body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleInternalServerErrorException(Exception e) {
        log.error("Internal server error", e);
        ErrorResponse errorResponse = ErrorResponse.fromErrorStatus(INTERNAL_SERVER_ERROR);
        return ResponseEntity.status(errorResponse.getCode())
                .body(errorResponse);
    }
}
