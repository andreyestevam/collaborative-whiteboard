// Manages connections, processes messages, and broadcasts updates.
package collaborative.whiteboard.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import collaborative.whiteboard.model.DrawingMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * ADD CLASS HEADER COMMENT
 */
public class WhiteboardHandler extends TextWebSocketHandler{
    /**
     * Maintains a thread-safe list of active WebSocket sessions.
     * This list is used to track all currently connected WebSocket sessions,
     * allowing the server to manage and broadcast messages to active participants.
     * Thread-safety is ensured by utilizing a CopyOnWriteArrayList for concurrent access scenarios.
     */
    private List<WebSocketSession> activeSessions = new CopyOnWriteArrayList<>();

    /**
     * Invoked after a new WebSocket connection has been established. This method adds the session
     * to the list of active WebSocket sessions and logs a message indicating that the connection
     * has been successfully created.
     *
     * @param session the WebSocket session that has been established
     * @throws Exception if an error occurs while processing the new session
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // Gets the query string and sets a default username.
        String query = session.getUri().getQuery();
        String username = "Unknown user";

        // Checks if the query string is valid (i.e. the user provided a username)
        if(query != null && query.contains("username=")){
            username = query.substring(query.indexOf("=") + 1);
        }

        // Stores the username, adds it to the active sessions list and lets users know someone joined the server.
        session.getAttributes().put("username", username);
        activeSessions.add(session);
        System.out.println(session.getAttributes().get("username") + " connected to the server.");
    }

    /**
     * Invoked after a WebSocket connection has been closed. This method removes the session from the
     * list of active sessions, retrieves the username associated with the session (if available), and
     * broadcasts a message to all active sessions notifying them that the user has left.
     *
     * @param session the WebSocket session that has been closed
     * @param status the status indicating the reason for the connection closure
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status){
        if(activeSessions.contains(session)){
            // Remove the closed connection's user from the activeSessions list and get its username, if any.
            activeSessions.remove(session);
            String username = (String) session.getAttributes().get("username");
            if(session.getAttributes().get("username") == null)
                username = "Unknown user";

            // Outputs that someone has left and broadcast it to all active users.
            System.out.println("Session closed: " + username);
            broadcastMessage("User " + username + " has left the room.", session);
        }
    }

    /**
     * Handles incoming text messages from a WebSocket client session. This method
     * processes the message payload, attempts to deserialize it into a
     * {@code DrawingMessage} object, and logs the details of the drawing operation.
     *
     * @param session the WebSocket session from which the message was received
     * @param message the text message containing the JSON payload
     * @throws Exception if an error occurs while handling the message
     */
    @Override
    public void handleTextMessage(WebSocketSession session,TextMessage message) throws Exception {

        // Extracts the JSON payload to a String.
        String payloadMessage = message.getPayload();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Deserialize the JSON into a DrawingMessage object.
            DrawingMessage draw = objectMapper.readValue(payloadMessage, DrawingMessage.class);

            // Output the message and broadcast it to all the active users.
            System.out.println("Message received from " + session.getAttributes().get("username") + ": "
                    + draw.getType() + " " + draw.getColor() + " " + draw.getShape());
            broadcastMessage(payloadMessage, session);
        } catch (JsonProcessingException e) {
            // Handle parsing errors, such as malformed JSON.
            System.out.println("Invalid JSON message received: " + payloadMessage);
            e.printStackTrace();
        }
    }

    /**
     * Broadcasts a JSON message to all active WebSocket sessions except the sender.
     *
     * @param jsonMessage the JSON-formatted message to be sent to all active sessions
     * @param senderSession the WebSocket session that sent the message, which will be excluded from the broadcast
     */
    private void broadcastMessage(String jsonMessage, WebSocketSession senderSession){
        // Iterate over the active users.
        for(WebSocketSession session : activeSessions){
            // Try sending the message to all the active users. If it does not work, output IOException.
            if(session.isOpen() && !session.equals(senderSession)) {
                try {
                    session.sendMessage(new TextMessage(jsonMessage));
                } catch (IOException e) {
                    // Output the message about the error.
                    System.err.println("Error sending message to " + session.getAttributes().get("username"));
                    e.printStackTrace();

                    // Remove the user from the activeSessions list.
                    activeSessions.remove(session);
                    System.out.println(session.getAttributes().get("username") + " disconnected from the server.");
                }
            }
        }
    }
}