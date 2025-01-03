package collaborative.whiteboard.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Collections;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@code DrawingMessage} class.
 * This class contains tests to validate the initialization,
 * serialization, and deserialization functionality of the {@code DrawingMessage} class.
 *
 * @author Andrey Estevam Seabra
 */
public class DrawingMessageTest {
    @Test
    public void testConstructorInitialization(){
        DrawingMessage drawingMessage = new DrawingMessage(
                "draw", "circle", "blue",
                Arrays.asList(0, 0, 0), Arrays.asList(1, 1, 0),
                Collections.singletonList(0));

        assertEquals("draw", drawingMessage.getType());
        assertEquals("circle", drawingMessage.getShape());
        assertEquals("blue", drawingMessage.getColor());
        assertEquals(Arrays.asList(0, 0, 0), drawingMessage.getStart());
        assertEquals(Arrays.asList(1, 1, 0), drawingMessage.getEnd());
        assertEquals(Collections.singletonList(0), drawingMessage.getRotation());
    }

    @Test
    public void testSerialization() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        DrawingMessage drawingMessage = new DrawingMessage(
                "draw", "line", "blue",
                Arrays.asList(0, 0, 0), Arrays.asList(1, 1, 0),
                null);

        // Serialize the drawingMessage into JSON
        String json = objectMapper.writeValueAsString(drawingMessage);
        assertEquals("{\"type\":\"draw\",\"shape\":\"line\",\"color\":\"blue\",\"start\":[0,0,0],\"end\":[1,1,0],\"rotation\":null}", json);

        // Deserialize the JSON into DrawingMessage
        DrawingMessage deserializedDrawingMessage = objectMapper.readValue(json, DrawingMessage.class);
        assertEquals("draw", deserializedDrawingMessage.getType());
        assertEquals("line", deserializedDrawingMessage.getShape());
        assertEquals("blue", deserializedDrawingMessage.getColor());
        assertEquals(Arrays.asList(0, 0, 0), deserializedDrawingMessage.getStart());
        assertEquals(Arrays.asList(1, 1, 0), deserializedDrawingMessage.getEnd());
        assertNull(deserializedDrawingMessage.getRotation());
    }
}
