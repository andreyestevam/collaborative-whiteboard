package collaborative.whiteboard.model;

import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Rectangle message.
 *
 * @author Andrey Estevam Seabra
 */
public class RectangleTest {

    @Test
    public void testDefaultConstructor() {
        Rectangle rectangle = new Rectangle();
        assertEquals(10, rectangle.getWidth());
        assertEquals(10, rectangle.getHeight());
        assertEquals(List.of(-5.0, 5.0, 0.0), rectangle.getTopLeftCorner());
        assertEquals(List.of(5.0, -5.0, 0.0), rectangle.getBottomRightCorner());
    }

    @Test
    public void testParameterizedConstructor() {
        List<Double> center = List.of(5.0, 5.0, 0.0);
        Rectangle rectangle = new Rectangle("draw", "blue", List.of(0.0), 10.0, 20.0, center);

        assertEquals("draw", rectangle.getType());
        assertEquals("rectangle", rectangle.getShape());
        assertEquals("blue", rectangle.getColor());
        assertEquals(10.0, rectangle.getWidth());
        assertEquals(20.0, rectangle.getHeight());
        assertEquals(center, rectangle.getCenter());
    }

    @Test
    public void testGetTopLeftCorner() {
        List<Double> center = List.of(5.0, 5.0, 0.0);
        Rectangle rectangle = new Rectangle("draw", "blue", List.of(0.0), 10.0, 20.0, center);

        List<Double> expectedTopLeft = List.of(0.0, 15.0, 0.0);
        assertEquals(expectedTopLeft, rectangle.getTopLeftCorner());
    }

    @Test
    public void testGetBottomRightCorner() {
        List<Double> center = List.of(5.0, 5.0, 0.0);
        Rectangle rectangle = new Rectangle("draw", "blue", List.of(0.0), 10.0, 20.0, center);

        List<Double> expectedBottomRight = List.of(10.0, -5.0, 0.0);
        assertEquals(expectedBottomRight, rectangle.getBottomRightCorner());
    }

    @Test
    public void testToString() {
        List<Double> center = List.of(5.0, 5.0, 0.0);
        Rectangle rectangle = new Rectangle("draw", "blue", List.of(0.0), 10.0, 20.0, center);

        String expectedString = "Rectangle{id=" + rectangle.getId() +
                ", type=draw, shape=rectangle, color=blue, center=[5.0, 5.0, 0.0], " +
                "width=10.0, height=20.0, topLeftCorner=[0.0, 15.0, 0.0], " +
                "bottomRightCorner=[10.0, -5.0, 0.0], rotation=[0.0]}";
        assertEquals(expectedString, rectangle.toString());
    }
}