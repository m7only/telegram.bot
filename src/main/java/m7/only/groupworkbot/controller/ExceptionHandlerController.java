package m7.only.groupworkbot.controller;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerController {
    // ----- FEEDBACK CONSTANT -----
    private static final String NOT_VALID_PARAMETERS = "Not valid parameters";
    private static final String INTERNAL_SERVER_ERROR = "Internal server error";
    // ----- FEEDBACK CONSTANT -----

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> illegalArgumentException(IllegalArgumentException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(NOT_VALID_PARAMETERS + ": " + exception.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> constraintViolationException(ConstraintViolationException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(NOT_VALID_PARAMETERS + ": " + exception.getMessage());
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> nullPointerException(NullPointerException exception) {
        exception.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(NOT_VALID_PARAMETERS + ": " + exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exception(Exception exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(INTERNAL_SERVER_ERROR + ": " + exception.getMessage());
    }
}
