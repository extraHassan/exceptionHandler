package com.obs.dqsc.api.controller.advice;

import com.obs.dqsc.api.exception.ErrorCode;
import com.obs.dqsc.api.exception.ErrorMessage;
import com.obs.dqsc.api.exception.functional.InvalidInputException;
import com.obs.dqsc.api.exception.functional.ResourceNotFoundException;
import com.obs.dqsc.api.exception.technical.LoggingAspectThrowableException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

/**
 * @author Othman CHAHBOUNE
 * @since 11-11-2021
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    public static final boolean INCLUDE_CLIENT_INFO = false;
    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * @param ex      an Exception
     * @param request the request from the client
     * @return a responseEntity with a descriptive message and some other information
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> globalExceptionHandler(Exception ex, WebRequest request) {
        ErrorMessage message = getErrorMessage(ex, request, ErrorCode.INTERNAL_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        LOG.error(ex.getMessage());
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * @param ex      an InvalidInputException
     * @param request the request from the client
     * @return a responseEntity with a descriptive message and some other information
     */
    @ExceptionHandler({InvalidInputException.class})
    public ResponseEntity<ErrorMessage> invalidInputExceptionHandler(Exception ex, WebRequest request) {
        ErrorMessage message = getErrorMessage(ex, request, ErrorCode.INVALID_INPUT_ERROR, HttpStatus.BAD_REQUEST);
        LOG.error(ex.getMessage());
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    /**
     * @param ex      an ResourceNotFoundException
     * @param request the request from the client
     * @return a responseEntity with a descriptive message and some other information
     */
    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<ErrorMessage> resourceNotFoundException(Exception ex, WebRequest request) {
        ErrorMessage message = getErrorMessage(ex, request, ErrorCode.RESOURCE_NOT_FOUND, HttpStatus.NOT_FOUND);
        LOG.error(ex.getMessage());
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    private ErrorMessage getErrorMessage(Exception ex, WebRequest request, ErrorCode errorCode, HttpStatus httptatus) {
        return new ErrorMessage(
                errorCode,
                httptatus.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(INCLUDE_CLIENT_INFO));
    }


}
