package collaborative.whiteboard;

import collaborative.whiteboard.handler.WhiteboardHandler;
import collaborative.whiteboard.model.DrawingMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.w3c.dom.Text;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@code WhiteboardHandler} class. This class ensures
 * that the functionalities related to WebSocket session management are
 * working as expected.
 *
 * @author Andrey Estevam Seabra
 */
public class WhiteboardHandlerTest{
    private WhiteboardHandler whiteboardHandler;
    private WebSocketSession sessionOne;
    private WebSocketSession sessionTwo;
    private URI uriOne;
    private URI uriTwo;

    @BeforeEach
    public void setUp() throws Exception{
        whiteboardHandler = new WhiteboardHandler();
        sessionOne = mock(WebSocketSession.class);
        sessionTwo = mock(WebSocketSession.class);
        uriOne = new URI("/whiteboard?username=UserOne");
        uriTwo = new URI("/whiteboard?username=UserTwo");
        Map<String, Object> attributesOne = new HashMap<>();
        Map<String, Object> attributesTwo = new HashMap<>();

        // Mock attributes map and the URI variables for each of the sessions.
        when(sessionOne.getUri()).thenReturn(uriOne);
        when(sessionTwo.getUri()).thenReturn(uriTwo);
        when(sessionOne.getAttributes()).thenReturn(attributesOne);
        when(sessionTwo.getAttributes()).thenReturn(attributesTwo);
    }

    @Test
    public void testAfterConnectionEstablished() throws Exception{
        // Simulate the connection establishment
        whiteboardHandler.afterConnectionEstablished(sessionOne);
        whiteboardHandler.afterConnectionEstablished(sessionTwo);

        assertTrue(whiteboardHandler.getActiveSessions().contains(sessionOne) &&
                whiteboardHandler.getActiveSessions().contains(sessionTwo));
        assertEquals("UserOne", sessionOne.getAttributes().get("username"));
        assertEquals("UserTwo", sessionTwo.getAttributes().get("username"));

        // Testing with missing or invalid query string.
        WebSocketSession sessionThree = mock(WebSocketSession.class);
        WebSocketSession sessionFour = mock(WebSocketSession.class);
        Map<String, Object> attributesThree = new HashMap<>();
        Map<String, Object> attributesFour = new HashMap<>();
        URI uriThree = new URI("/whiteboard");
        URI uriFour = new URI("/whiteboard?username=");

        when(sessionThree.getUri()).thenReturn(uriThree);
        when(sessionFour.getUri()).thenReturn(uriFour);
        when(sessionThree.getAttributes()).thenReturn(attributesThree);
        when(sessionFour.getAttributes()).thenReturn(attributesFour);

        whiteboardHandler.afterConnectionEstablished(sessionThree);
        whiteboardHandler.afterConnectionEstablished(sessionFour);

        assertTrue(whiteboardHandler.getActiveSessions().contains(sessionThree));
        assertTrue(whiteboardHandler.getActiveSessions().contains(sessionFour));
        assertEquals("Unknown User", sessionThree.getAttributes().get("username"));
        assertEquals("Unknown User", sessionFour.getAttributes().get("username"));
    }

    @Test
    public void testAfterConnectionClosed() throws Exception{
        whiteboardHandler.afterConnectionEstablished(sessionOne);
        whiteboardHandler.afterConnectionEstablished(sessionTwo);
        assertTrue(whiteboardHandler.getActiveSessions().contains(sessionOne) &&
                whiteboardHandler.getActiveSessions().contains(sessionTwo));

        whiteboardHandler.afterConnectionClosed(sessionOne, null);
        whiteboardHandler.afterConnectionClosed(sessionTwo, CloseStatus.SERVER_ERROR);

        assertFalse(whiteboardHandler.getActiveSessions().contains(sessionOne));
        assertFalse(whiteboardHandler.getActiveSessions().contains(sessionTwo));
    }

    @Test
    public void testHandleTextMessage() throws Exception{
        when(sessionOne.isOpen()).thenReturn(true);
        when(sessionTwo.isOpen()).thenReturn(true);
        String validJson = new ObjectMapper().writeValueAsString(new DrawingMessage(
                "draw", "circle", "blue", List.of(0,0,0), List.of(1,1,0), null));
        whiteboardHandler.afterConnectionEstablished(sessionOne);
        whiteboardHandler.afterConnectionEstablished(sessionTwo);

        whiteboardHandler.handleTextMessage(sessionOne, new TextMessage(validJson));

        verify(sessionTwo).sendMessage(new TextMessage(validJson));
        verify(sessionOne, never()).sendMessage(any(TextMessage.class));

        // Testing malformed JSON
        String malformedJson = "{invalid json}";
        String invalidJson = "invalid json";
        whiteboardHandler.handleTextMessage(sessionOne, new TextMessage(malformedJson));
        whiteboardHandler.handleTextMessage(sessionTwo, new TextMessage(invalidJson));
        verify(sessionOne, never()).sendMessage(any(TextMessage.class));
    }

    @Test
    public void testHandleTransportError() throws Exception{
        when(sessionOne.isOpen()).thenReturn(true);
        when(sessionTwo.isOpen()).thenReturn(false);
        whiteboardHandler.afterConnectionEstablished(sessionOne);
        whiteboardHandler.afterConnectionEstablished(sessionTwo);

        RuntimeException transportException = new RuntimeException("Transport error");
        whiteboardHandler.handleTransportError(sessionOne, transportException);
        whiteboardHandler.handleTransportError(sessionTwo, transportException);
        assertFalse(whiteboardHandler.getActiveSessions().contains(sessionOne));
        assertFalse(whiteboardHandler.getActiveSessions().contains(sessionTwo));
        verify(sessionOne, never()).sendMessage(any(TextMessage.class));
    }

    @Test
    public void testRapidConnectionAndDisconnection() throws Exception{
        long startTime = System.nanoTime();
        Vector<WebSocketSession> sessionsList = new Vector<>(100);
        for(int i = 0; i < 100; i++){
            WebSocketSession session = mock(WebSocketSession.class);
            URI uri = new URI("/whiteboard?username=User" + i);
            Map<String, Object> attributes = new HashMap<>();
            when(session.getUri()).thenReturn(uri);
            when(session.getAttributes()).thenReturn(attributes);
            when(session.isOpen()).thenReturn(true);
            sessionsList.add(session);
            whiteboardHandler.afterConnectionEstablished(session);
            assertTrue(whiteboardHandler.getActiveSessions().contains(session));
            assertEquals("User" + i, session.getAttributes().get("username"));
        }

        for(int i = 0; i < 100; i++){
            whiteboardHandler.afterConnectionClosed(sessionsList.get(i), CloseStatus.NORMAL);
            assertFalse(whiteboardHandler.getActiveSessions().contains(sessionsList.get(i)));
        }

        assertTrue(whiteboardHandler.getActiveSessions().isEmpty());
        long endTime = System.nanoTime();
        long durationInSeconds = (endTime - startTime) / 1000000;
        System.out.println("Total time taken for " + sessionsList.size() + " rapid connections and disconnections: " + durationInSeconds + " milliseconds.");
    }

    @Test
    public void testHighTrafficConnections() throws Exception{
        // Simulate connections
        long startTime = System.nanoTime();
        Vector<WebSocketSession> sessionsList = new Vector<>(100);
        for(int i = 0; i < 100; i++){
            WebSocketSession session = mock(WebSocketSession.class);
            URI uri = new URI("/whiteboard?username=User" + i);
            Map<String, Object> attributes = new HashMap<>();
            when(session.getUri()).thenReturn(uri);
            when(session.getAttributes()).thenReturn(attributes);
            when(session.isOpen()).thenReturn(true);
            sessionsList.add(session);
            whiteboardHandler.afterConnectionEstablished(session);
            assertTrue(whiteboardHandler.getActiveSessions().contains(session));
            assertEquals("User" + i, session.getAttributes().get("username"));
        }
        assertEquals(100, whiteboardHandler.getActiveSessions().size());

        // Simulate messages
        String validJson = new ObjectMapper().writeValueAsString(new DrawingMessage(
                "draw", "circle", "blue", List.of(0,0,0), List.of(1,1,0), null));
        for(WebSocketSession session : sessionsList){
            whiteboardHandler.handleTextMessage(session, new TextMessage(validJson));
        }

        // Check if every session received the message at least once per user and simulate disconnections
        for(WebSocketSession session : sessionsList){
            verify(session, times(99)).sendMessage(new TextMessage(validJson));
            whiteboardHandler.afterConnectionClosed(session, null);
        }
        assertTrue(whiteboardHandler.getActiveSessions().isEmpty());
        long endTime = System.nanoTime();
        long durationInSeconds = (endTime - startTime) / 1000000;
        System.out.println("Total time taken for " + sessionsList.size() + " rapid connections and disconnections: " + durationInSeconds + " milliseconds.");
    }
}