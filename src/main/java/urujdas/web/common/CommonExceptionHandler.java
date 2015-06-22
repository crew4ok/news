package urujdas.web.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import urujdas.dao.exception.NotFoundException;
import urujdas.service.exception.PullUpTooFrequentException;
import urujdas.web.common.model.error.ErrorResponse;
import urujdas.web.common.model.error.ErrorType;
import urujdas.web.common.model.error.NotFoundErrorResponse;
import urujdas.web.common.model.error.ValidationErrorResponse;
import urujdas.web.exception.ValidationException;

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
        Long id = e.getId();

        LOG.debug("Entity of class = {} with id = {} was not found", entityName, id);

        return new ResponseEntity<>(
                new NotFoundErrorResponse(entityName, id),
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
