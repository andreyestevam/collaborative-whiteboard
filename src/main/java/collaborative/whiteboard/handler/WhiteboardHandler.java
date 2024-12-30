// Manages connections, processes messages, and broadcasts updates.
package collaborative.whiteboard.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import collaborative.whiteboard.model.DrawingMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

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

            // Output the message.
            System.out.println("Message received from " + session.getAttributes().get("username") + ": " + draw.getType() + " " + draw.getColor() + " " + draw.getShape());
        } catch (JsonProcessingException e) {
            // Handle parsing errors, such as malformed JSON.
            System.out.println("Invalid JSON message received: " + payloadMessage);
            e.printStackTrace();
        }
    }
}