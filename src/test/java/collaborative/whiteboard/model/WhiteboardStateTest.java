package collaborative.whiteboard.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

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
        assertEquals(new ConcurrentHashMap<>(), whiteboardState.getDrawingMessages());
        assertNotNull(whiteboardState.getTimeStamp());
        assertEquals(1, whiteboardState.getVersion());
    }

    @Test
    public void testSettersAndGetters(){
        assertEquals(new ConcurrentHashMap<>(), whiteboardState.getDrawingMessages());
        assertNotNull(whiteboardState.getTimeStamp());
        assertEquals(1, whiteboardState.getVersion());

        Map<String, DrawingMessage> drawingMessages = new ConcurrentHashMap<>();
        for(int i = 0; i < 10; i++){
            DrawingMessage newDrawingMessage = new DrawingMessage("draw", "circle", "blue", null);
            drawingMessages.put(newDrawingMessage.getId(), newDrawingMessage);
        }
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Timestamp(System.currentTimeMillis()));
        whiteboardState.setDrawingMessages(drawingMessages);
        whiteboardState.setVersion(2);
        whiteboardState.setTimeStamp(timeStamp);

        assertEquals(drawingMessages, whiteboardState.getDrawingMessages());
        assertEquals(2, whiteboardState.getVersion());
        assertEquals(timeStamp, whiteboardState.getTimeStamp());
    }

    @Test
    public void testToString(){
        Map<String, DrawingMessage> drawingMessages = new ConcurrentHashMap<>();
        whiteboardState.setDrawingMessages(drawingMessages);
        whiteboardState.setVersion(1);
        whiteboardState.setTimeStamp("2025-01-02T10:15:30");

        String expectedString = "WhiteboardState{drawingMessages=[], " +
                "timeStamp='2025-01-02T10:15:30', version=1}";
        assertEquals(expectedString, whiteboardState.toString());
    }

    @Test
    public void testExportToJSON() throws IOException {
        DrawingMessage drawingMessage = new DrawingMessage("draw", "circle", "blue", null);
        whiteboardState.addDrawingMessage(drawingMessage);
        String json = whiteboardState.exportToJSON();
        assertTrue(json.contains("\"type\":\"draw\""));
        assertTrue(json.contains("\"shape\":\"circle\""));
        assertTrue(json.contains("\"color\":\"blue\""));
        assertTrue(json.contains("\"id\":\"" + drawingMessage.getId() + "\""));
    }

    @Test
    public void testImportFromJSON() throws IOException {
        String json = "{\"drawingMessages\":{\"1\":{\"id\":\"1\",\"type\":\"draw\",\"shape\":\"circle\",\"color\":\"blue\",\"rotation\":null}},\"timeStamp\":\"2025-01-02T10:15:30\",\"version\":1}";
        WhiteboardState deserializedState = WhiteboardState.importFromJSON(json);

        assertEquals(1, deserializedState.getVersion());
        assertEquals("2025-01-02T10:15:30", deserializedState.getTimeStamp());
        assertEquals(1, deserializedState.getDrawingMessages().size());

        DrawingMessage drawingMessage = deserializedState.getDrawingMessages().get("1");
        assertEquals("draw", drawingMessage.getType());
        assertEquals("circle", drawingMessage.getShape());
        assertEquals("blue", drawingMessage.getColor());
        assertNull(drawingMessage.getRotation());
    }

    @Test
    public void shouldAddDifferentShapes(){
        DrawingMessage circleOne = new Circle("draw", "red", null, 5.0, Collections.singletonList(0.0));
        Circle circleTwo = new Circle("draw", "green", null, 3.0, Collections.singletonList(0.0));
        whiteboardState.addDrawingMessage(circleOne);
        whiteboardState.addDrawingMessage(circleTwo);

        assertEquals(2, whiteboardState.getDrawingMessages().size());
        assertEquals(circleOne, whiteboardState.getDrawingMessages().get(circleOne.getId()));
        assertEquals(circleTwo, whiteboardState.getDrawingMessages().get(circleTwo.getId()));
    }

    @Test
    public void shouldThrowException(){
        assertThrows(IllegalArgumentException.class, () -> {
            whiteboardState.setDrawingMessages(null);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            whiteboardState.setVersion(0);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            whiteboardState.setTimeStamp("0");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            whiteboardState.setTimeStamp(null);
        });
    }
}