package collaborative.whiteboard.manager;

import collaborative.whiteboard.handler.WhiteboardHandler;
import collaborative.whiteboard.model.WhiteboardState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.Collections;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
/**
 * Unit testing class for StateManager class.
 *
 * @author Andrey Estevam Seabra
 */
public class StateManagerTest {
    private StateManager stateManager;

    @Mock
    private WhiteboardHandler whiteboardHandler;

    @Mock
    private WebSocketSession mockSession;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        stateManager = new StateManager(whiteboardHandler);
    }

    @Test
    public void testInitialStateIsNull(){
        assertNull(stateManager.getCurrentState(), "Initial state should be null.");
    }

    @Test
    public void testAddActionUpdatesStateAndClearsRedoStack(){
        WhiteboardState initialState = new WhiteboardState();
        WhiteboardState newState = new WhiteboardState();
        stateManager.addAction(initialState);

        assertEquals(initialState, stateManager.getCurrentState());

        stateManager.addAction(newState);
        assertEquals(newState, stateManager.getCurrentState());

        // Verify redo stack is cleared.
        stateManager.undo();
        stateManager.redo();
        stateManager.addAction(initialState);
        assertTrue(stateManager.getRedoStack().isEmpty());
    }

    @Test
    public void testUndoRestoresPreviousState(){
        WhiteboardState initialState = new WhiteboardState();
        WhiteboardState newState = new WhiteboardState();
        stateManager.addAction(initialState);
        stateManager.addAction(newState);

        stateManager.undo();
        assertEquals(initialState, stateManager.getCurrentState());
    }

    @Test
    public void testUndoWhenUndoIsEmpty(){
        stateManager.undo();
        assertNull(stateManager.getCurrentState());
    }

    @Test
    public void testRedoRestoresPreviousUndo(){
        WhiteboardState initialState = new WhiteboardState();
        WhiteboardState newState = new WhiteboardState();
        stateManager.addAction(initialState);
        stateManager.addAction(newState);

        stateManager.undo();
        stateManager.redo();
        assertEquals(newState, stateManager.getCurrentState());
    }

    @Test
    public void testRedoWhenRedoIsEmpty(){
        stateManager.redo();
        assertNull(stateManager.getCurrentState());
    }

    @Test
    public void testBroadcastState() throws Exception {
        WhiteboardState state = new WhiteboardState();
        stateManager.addAction(state);

        when(whiteboardHandler.getActiveSessions()).thenReturn(Collections.singletonList(mockSession));
        when(mockSession.isOpen()).thenReturn(true);

        stateManager.broadcastState();

        verify(mockSession).sendMessage(any(TextMessage.class));
    }

    @Test
    public void testBroadcastStateHandlesClosedSessions() throws Exception{
        WhiteboardState state = new WhiteboardState();
        stateManager.addAction(state);

        when(whiteboardHandler.getActiveSessions()).thenReturn(Collections.singletonList(mockSession));
        when(mockSession.isOpen()).thenReturn(false);

        stateManager.broadcastState();

        verify(mockSession, never()).sendMessage(any(TextMessage.class));
    }
}