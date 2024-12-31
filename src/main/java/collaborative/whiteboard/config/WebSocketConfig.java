package collaborative.whiteboard.config;

import collaborative.whiteboard.CollaborativeWhiteboardApplication;
import collaborative.whiteboard.handler.WhiteboardHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * This class implements a WebSocket configuration class that defines the URL for the WebSocket
 * endpoint (/whiteboard), as well as allows cross-origin requests for local testing.
 *
 * @author Andrey Estevam Seabra
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    /**
     * Defines the WebSocket endpoint URL that clients can use to establish a connection
     * with the server. This constant is used to register the endpoint within the WebSocket
     * configuration to handle real-time communication.
     */
    private static final String WEB_SOCKET_ENDPOINT = "/whiteboard";

    /**
     * Registers WebSocket handlers to configure WebSocket support for the application.
     * This method defines the WebSocket endpoint and sets options for cross-origin requests.
     *
     * @param registry the WebSocketHandlerRegistry used to configure WebSocket endpoints
     */
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // Register WebSocket endpoint with a URL
        registry.addHandler(new WhiteboardHandler(), WEB_SOCKET_ENDPOINT)
                .setAllowedOrigins("*"); // Allow cross-origin requests
    }
}