package collaborative.whiteboard.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * A global exception handler that intercepts specific exceptions thrown and provides appropriate
 * responses to the client. It is annotated with @ControllerAdvice to indicate that it handles
 * exceptions globally across multiple controllers.
 *
 * @author Andrey Estevam Seabra
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles IllegalArgumentException.
     *
     * @param e the given exception that was thrown.
     * @return a 400 Bad Request response.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    /**
     * Handles NullPointerException.
     *
     * @param e the given exception that was thrown.
     * @return a 500 Internal Server Error response.
     */
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> handleNullPointerException(NullPointerException e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
    }
}