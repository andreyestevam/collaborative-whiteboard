package collaborative.whiteboard.controller;

import collaborative.whiteboard.controller.exception.GlobalExceptionHandler;
import collaborative.whiteboard.manager.StateManager;
import collaborative.whiteboard.model.WhiteboardState;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
     * Manages the state of the Whiteboard.
     */
    private final StateManager stateManager;

    /**
     * Constructs a new WhiteboardController instance.
     *
     * @param stateManager the StateManager responsible for managing the whiteboard state.
     */
    public WhiteboardController(StateManager stateManager){
        this.stateManager = stateManager;
    }

    /**
     * Saves the current state of the collaborative whiteboard.
     *
     * @param state the state of the whiteboard to be saved.
     * @return a confirmation message about the successful save.
     */
    @PostMapping("/save")
    public ResponseEntity<String> saveState(@RequestBody WhiteboardState state){
        System.out.println("Saving state: " + state);
        if(state.getTimeStamp() == null || state.getTimeStamp().isEmpty()){
            throw new IllegalArgumentException("Timestamp cannot be null or empty."); // Handled by GlobalExceptionHandler.
        }
        if(state.getVersion() <= 0){
            throw new IllegalArgumentException("Version must be greater than 0.");
        }
        stateManager.addAction(state);
        stateManager.broadcastState();
        return ResponseEntity.ok("Whiteboard current state successfully saved.");
    }

    /**
     * Loads the current state of the collaborative whiteboard.
     * If there is no existing state, returns a new, empty WhiteboardState instance.
     *
     * @return the current state of the whiteboard or a new WhiteboardState if no state exists.
     */
    @GetMapping("/load")
    public WhiteboardState loadState(){
        if(stateManager.getCurrentState() == null){
            System.out.println("No state found.");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No state found.");
        }
        System.out.println("Loaded state: " + stateManager.getCurrentState());
        return stateManager.getCurrentState(); // Return the last saved state.
    }

    /**
     * Reverts the whiteboard state to the most recent action in the undo stack
     * and broadcasts the updated state to all connected clients. If the undo stack
     * is empty, no state changes are made.
     *
     * @return a confirmation message indicating the undo operation was successful.
     */
    @PostMapping("/undo")
    public String undo(){
        stateManager.undo();
        stateManager.broadcastState();
        return "Undo successful.";
    }

    /**
     * Restores the most recent state from the redo stack, sets it as the current state,
     * and broadcasts the updated state to all connected clients.
     * If the redo stack is empty, no state change is made.
     *
     * @return a confirmation message indicating the redo operation was successful.
     */
    @PostMapping("/redo")
    public String redo(){
        stateManager.redo();
        stateManager.broadcastState();
        return "Redo successful.";
    }

    /**
     * Retrieves the current state of the collaborative whiteboard.
     *
     * @return the current state of the whiteboard.
     */
    @GetMapping("/currentState")
    public WhiteboardState getCurrentState(){
        return stateManager.getCurrentState();
    }
}