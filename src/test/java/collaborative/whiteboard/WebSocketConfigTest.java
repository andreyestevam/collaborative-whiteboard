package collaborative.whiteboard;

import collaborative.whiteboard.config.WebSocketConfig;
import collaborative.whiteboard.handler.WhiteboardHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistration;

import static org.mockito.Mockito.*;

/**
 * A test class for verifying the behavior and configuration of the WebSocket configuration
 * defined in the {@link WebSocketConfig} class.
 *
 * @author Andrey Estevam Seabra
 */
@SpringBootTest
@WebAppConfiguration
public class WebSocketConfigTest {
    @Autowired
    private WebSocketConfig webSocketConfig;

    @Test
    public void shouldRegisterWebSocketHandlersAndAllowCrossOriginRequests(){
        WebSocketHandlerRegistry handlerRegistry = mock(WebSocketHandlerRegistry.class);
        WebSocketHandlerRegistration handlerRegistration = mock(WebSocketHandlerRegistration.class);
        when(handlerRegistry.addHandler(any(WebSocketHandler.class), eq("/whiteboard"))).thenReturn(handlerRegistration);

        // Mock setAllowedOrigins to allow chaining
        when(handlerRegistration.setAllowedOrigins("*")).thenReturn(handlerRegistration);
        webSocketConfig.registerWebSocketHandlers(handlerRegistry);

        // Verify addHandler is called with the correct parameters
        verify(handlerRegistry, times(1)).addHandler(any(WhiteboardHandler.class), eq("/whiteboard"));

        // Verify setAllowedOrigins is called on the handlerRegistration
        verify(handlerRegistration, times(1)).setAllowedOrigins("*");
    }
}