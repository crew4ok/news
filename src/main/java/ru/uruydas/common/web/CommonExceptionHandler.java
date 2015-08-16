package ru.uruydas.common.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import ru.uruydas.common.dao.exception.NotFoundException;
import ru.uruydas.common.service.exception.PullUpTooFrequentException;
import ru.uruydas.common.web.exception.ValidationException;
import ru.uruydas.common.web.model.error.ErrorResponse;
import ru.uruydas.common.web.model.error.ErrorType;
import ru.uruydas.common.web.model.error.NotFoundErrorResponse;
import ru.uruydas.common.web.model.error.ValidationErrorResponse;

@ControllerAdvice(annotations = RestController.class)
public class CommonExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(CommonExceptionHandler.class);

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ValidationErrorResponse> validationException(ValidationException e) {
        LOG.debug("Validation error", e);

        return new ResponseEntity<>(
                new ValidationErrorResponse(e.getErrors()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(PullUpTooFrequentException.class)
    public ResponseEntity<ErrorResponse> pullUpToFrequentException(PullUpTooFrequentException e) {
        LOG.debug("Pull to frequent");

        return new ResponseEntity<>(
                new ErrorResponse(ErrorType.PULL_UP_TOO_FREQUENT, "Pull up is too frequent"),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<NotFoundErrorResponse> notFoundException(NotFoundException e) {
        String entityName = e.getEntityClass().getSimpleName();
        Object identifier = e.getIdentifier();

        LOG.debug("Entity of class = {} was not found by identifier = {}", entityName, identifier);

        return new ResponseEntity<>(
                new NotFoundErrorResponse(entityName, identifier),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(HttpMediaTypeException.class)
    public ResponseEntity<ErrorResponse> httpMediaTypeException(HttpMediaTypeException e) {
        LOG.debug("Media type is not supported", e);

        return new ResponseEntity<>(
                new ErrorResponse(ErrorType.WRONG_FORMAT, "Content-Type or Accept is incorrect"),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> messageNotReadable(HttpMessageNotReadableException e) {
        LOG.debug("Message is not readable", e);

        return new ResponseEntity<>(
                new ErrorResponse(ErrorType.WRONG_FORMAT, "Payload is incorrect"),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> genericExceptionHandler(Exception e) {
        LOG.error("Exception happened", e);

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
