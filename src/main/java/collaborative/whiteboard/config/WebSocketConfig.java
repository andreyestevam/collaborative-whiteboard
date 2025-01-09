package collaborative.whiteboard.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * WebSocket configuration for using STOMP protocol with a message broker.
 * Defines the WebSocket endpoint and message broker configuration.
 *
 * @author Andrey Estevam Seabra
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic"); // Enables a simple in-memory message broker
        registry.setApplicationDestinationPrefixes("/app"); // Prefix for messages sent to the server
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/whiteboard") // WebSocket STOMP endpoint
                .setAllowedOrigins("http://localhost:3000") // Allow CORS for frontend
                .withSockJS(); // Enable SockJS fallback
    }
}