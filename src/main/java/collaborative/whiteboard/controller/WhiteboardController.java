package collaborative.whiteboard.controller;

import collaborative.whiteboard.controller.exception.GlobalExceptionHandler;
import collaborative.whiteboard.model.WhiteboardState;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

/**
 * The WhiteboardController class serves as a REST API controller for managing
 * the whiteboard state. It provides endpoints for saving and loading the whiteboard,
 * allowing real-time synchronization of whiteboard data.
 *
 * @author Andrey Estevam Seabra
 */
@RestController
@RequestMapping("/api/whiteboard")
public class WhiteboardController {
    /**
     * Holds the current state of the collaborative whiteboard. This state includes
     * all the drawing actions, timestamp, and version information to facilitate
     * real-time updates and synchronization between users. It serves as the shared
     * source of truth for whiteboard interactions.
     */
    private static WhiteboardState currentState;

    /**
     * Saves the current state of the collaborative whiteboard.
     *
     * @param state the state of the whiteboard to be saved.
     * @return a confirmation message about the successful save.
     */
    @PostMapping("/save")
    public String saveState(@RequestBody WhiteboardState state){
        if(state.getTimeStamp() == null || state.getTimeStamp().isEmpty()){
            throw new IllegalArgumentException("Timestamp cannot be null or empty."); // Handled by GlobalExceptionHandler.
        }
        if(state.getVersion() <= 0){
            throw new IllegalArgumentException("Version must be greater than 0.");
        }
        currentState = state;
        return "Whiteboard current state successfully saved.";
    }

    /**
     * Loads the current state of the collaborative whiteboard.
     * If there is no existing state, returns a new, empty WhiteboardState instance.
     *
     * @return the current state of the whiteboard or a new WhiteboardState if no state exists.
     */
    @GetMapping("/load")
    public WhiteboardState loadState(){
        if(currentState == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No state found.");
        }
        return currentState; // Return the last saved state.
    }
}