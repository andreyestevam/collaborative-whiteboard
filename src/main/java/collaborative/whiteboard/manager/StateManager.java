package collaborative.whiteboard.manager;

import collaborative.whiteboard.handler.WhiteboardHandler;
import collaborative.whiteboard.model.WhiteboardState;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.List;
import java.util.Stack;

/**
 * Responsible for managing the state transitions of a whiteboard application.
 * Maintains the current state of the whiteboard and provides mechanisms to undo, redo,
 * and add new states. States are stored in a stack-based structure for efficient navigation
 * through the history of actions.
 *
 * @author Andrey Estevam Seabra
 */
@Component
public class StateManager {
    /**
     * Store the current WhiteboardState.
     */
    private WhiteboardState currentState;
    /**
     * Store the stack of states that can be undone.
     */
    private Stack<WhiteboardState> undoStack;
    /**
     * Store the stack of states that can be redone.
     */
    private Stack<WhiteboardState> redoStack;
    /**
     * Handle interactions with the whiteboard.
     */
    private WhiteboardHandler whiteboardHandler;

    /**
     * Default constructor.
     */
    @Autowired
    public StateManager(WhiteboardHandler whiteboardHandler) {
        currentState = null;
        undoStack = new Stack<>();
        redoStack = new Stack<>();
        this.whiteboardHandler = whiteboardHandler;
    }

    /**
     * Getter method for currentState.
     *
     * @return the variable currentState.
     */
    public WhiteboardState getCurrentState() {return currentState;}

    /**
     * Updates the current state of the whiteboard by saving the existing state to the undo stack,
     * clearing the redo stack, and setting the provided state as the current state.
     *
     * @param state the new state to be added as the current state of the whiteboard.
     */
    public void addAction(WhiteboardState state){
        undoStack.push(currentState);
        redoStack.clear();
        currentState = state;
    }

    /**
     * Reverts the current state of the whiteboard to the most recent state
     * in the undo stack, if available. The current state is saved to the
     * redo stack to enable redoing the action.
     *
     * If the undo stack is empty, no action is performed.
     */
    public void undo(){
        if(!undoStack.empty()){
            redoStack.push(currentState);
            currentState = undoStack.pop();
        }
    }

    /**
     * Restores the most recent state from the redo stack and sets it as the current state,
     * if the redo stack is not empty. The current state is pushed onto the undo stack to
     * maintain a record of the states for possible undo operations.
     *
     * If the redo stack is empty, no action is performed.
     */
    public void redo(){
        if(!redoStack.empty()){
            undoStack.push(currentState);
            currentState = redoStack.pop();
        }
    }

    /**
     * Broadcasts the current state of the whiteboard to all active WebSocket sessions.
     *
     * This method serializes the current state into JSON and sends it as a message to
     * all connected and open sessions managed by the WhiteboardHandler. If an I/O error
     * occurs during the broadcasting process, the exception is logged in the standard error output.
     */
    public void broadcastState(){
        try{
            // Serializes the currentState into JSON.
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonMessage = objectMapper.writeValueAsString(currentState);
            System.out.println("Sending message: " + jsonMessage);

            // Notify all the users of the currentState of the board.
            for(WebSocketSession session : whiteboardHandler.getActiveSessions()){
                if(session.isOpen()){
                    session.sendMessage(new TextMessage(jsonMessage));
                }
            }
        }catch(IOException e){
            System.err.println("Error broadcasting state: " + e.getMessage());
            e.printStackTrace();
        }
    }
}