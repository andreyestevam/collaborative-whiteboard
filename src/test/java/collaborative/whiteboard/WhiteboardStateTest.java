package collaborative.whiteboard;

import static org.junit.jupiter.api.Assertions.*;

import collaborative.whiteboard.model.DrawingMessage;
import collaborative.whiteboard.model.WhiteboardState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Arrays;

/**
 * Unit tests for the {@code WhiteboardState} class.
 *
 * @author Andrey Estevam Seabra
 */
public class WhiteboardStateTest {
    private WhiteboardState whiteboardState;

    @BeforeEach
    public void setUp() {
        whiteboardState = new WhiteboardState();
    }

    @Test
    public void testConstructorInitialization(){
        assertNull(whiteboardState.getDrawingMessages());
        assertNull(whiteboardState.getTimeStamp());
        assertEquals(0, whiteboardState.getVersion());
    }

    @Test
    public void testSettersAndGetters(){
        assertNull(whiteboardState.getDrawingMessages());
        assertNull(whiteboardState.getTimeStamp());
        assertEquals(0, whiteboardState.getVersion());

        List<DrawingMessage> drawingMessages = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            drawingMessages.add(new DrawingMessage("draw",
                    "circle", "blue", Arrays.asList(0, 0, 0),
                    Arrays.asList(1, 1, 0), null));
        }
        String timeStamp = new Date().toString();
        whiteboardState.setDrawingMessages(drawingMessages);
        whiteboardState.setVersion(2);
        whiteboardState.setTimeStamp(timeStamp);

        assertEquals(drawingMessages, whiteboardState.getDrawingMessages());
        assertEquals(2, whiteboardState.getVersion());
        assertEquals(timeStamp, whiteboardState.getTimeStamp());
    }

    @Test
    public void testToString(){
        whiteboardState.setDrawingMessages(Arrays.asList());
        whiteboardState.setVersion(1);
        whiteboardState.setTimeStamp("2025-01-02T10:15:30Z");

        String expectedString = "WhiteboardState{drawingMessages=[], " +
                "timeStamp=2025-01-02T10:15:30Z, version=1}";
        assertEquals(expectedString, whiteboardState.toString());
    }
}