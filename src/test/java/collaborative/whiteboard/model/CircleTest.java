package collaborative.whiteboard.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Circle message.
 *
 * @author Andrey Estevam Seabra
 */
public class CircleTest {

    @Test
    public void testConstructorInitialization(){
        Circle circle = new Circle("draw", "red",null, 5.0, Collections.singletonList(0.0));

        assertEquals("draw", circle.getType());
        assertEquals("circle", circle.getShape());
        assertEquals("red", circle.getColor());
        assertNull(circle.getRotation());
        assertEquals(5.0, circle.getRadius());
    }

    @Test
    public void testSettersAndGetters() {
        Circle circle = new Circle();
        circle.setType("draw");
        circle.setShape("circle");
        circle.setColor("blue");
        circle.setRadius(10.0);

        assertEquals("draw", circle.getType());
        assertEquals("circle", circle.getShape());
        assertEquals("blue", circle.getColor());
        assertEquals(10.0, circle.getRadius());
    }

    @Test
    public void testToString() {
        Circle circle = new Circle("draw", "green", null, 3.0, List.of(0.0, 0.0, 0.0));

        String expected = "Circle{id=" + circle.getId() + ", type=draw, shape=circle, color=green, center=[0.0, 0.0, 0.0], radius=3.0, rotation=null}";
        assertEquals(expected, circle.toString());
    }

    @Test
    public void testSerializationAndDeserialization() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        Circle circle = new Circle("draw", "blue", null, 7.5, List.of(0.0, 1.0, 0.0));

        // Serialize the circle into JSON.
        String json = objectMapper.writeValueAsString(circle);
        assertTrue(json.contains("\"shape\":\"circle\""));
        assertTrue(json.contains("\"radius\":7.5"));

        // Deserialize the JSON back into a Circle object.
        Circle deserializedCircle = objectMapper.readValue(json, Circle.class);
        assertEquals("draw", deserializedCircle.getType());
        assertEquals("circle", deserializedCircle.getShape());
        assertEquals("blue", deserializedCircle.getColor());
        assertEquals(7.5, deserializedCircle.getRadius());
        assertEquals(List.of(0.0, 1.0, 0.0), deserializedCircle.getCenter());
        assertEquals(circle.getId(), deserializedCircle.getId());
        assertEquals(circle.getRotation(), deserializedCircle.getRotation());
    }
}
