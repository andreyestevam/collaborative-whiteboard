package collaborative.whiteboard.controller;

import collaborative.whiteboard.model.WhiteboardState;
import org.springframework.web.bind.annotation.*;

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
        currentState = state;
        System.out.println("Current state saved.");
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
            return new WhiteboardState(); // Return an empty state.
        }
        return currentState; // Return the last saved state.
    }
}