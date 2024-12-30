/**
 * This class implements a WebSocket configuration class that defines the URL for the WebSocket
 * endpoint (/whiteboard), as well as allows cross-origin requests for local testing.
 *
 * @author Andrey Estevam Seabra
 */
package collaborative.whiteboard.config;
package collaborative.whiteboard.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private static final String WEB_SOCKET_ENDPOINT = "/whiteboard";

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // Register WebSocket endpoint with a URL
        registry.addHandler(new CustomWebSocketHandler(), WEB_SOCKET_ENDPOINT)
                .setAllowedOrigins("*"); // Allow cross-origin requests
    }
}