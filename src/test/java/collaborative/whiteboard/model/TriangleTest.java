package collaborative.whiteboard.model;

import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Triangle message.
 *
 * @author Andrey Estevam Seabra
 */
public class TriangleTest {
    @Test
    public void testDefaultConstructor() {
        Triangle triangle = new Triangle();

        List<List<Double>> expectedVertices = List.of(
                List.of(-2.0, -2.0, 0.0),
                List.of(2.0, -2.0, 0.0),
                List.of(0.0, 2.0, 0.0)
        );

        assertEquals(expectedVertices, triangle.getVertices());
        assertEquals("triangle", triangle.getShape());
        assertNotNull(triangle.getId());
    }

    @Test
    public void testParameterizedConstructor() {
        List<List<Double>> vertices = List.of(
                List.of(1.0, 1.0, 0.0),
                List.of(2.0, 3.0, 0.0),
                List.of(4.0, 1.0, 0.0)
        );
        Triangle triangle = new Triangle("draw", "green", List.of(0.0), vertices);

        assertEquals("draw", triangle.getType());
        assertEquals("triangle", triangle.getShape());
        assertEquals("green", triangle.getColor());
        assertEquals(vertices, triangle.getVertices());
        assertNotNull(triangle.getId());
    }

    @Test
    public void testSetVertices() {
        Triangle triangle = new Triangle();
        List<List<Double>> newVertices = List.of(
                List.of(0.0, 0.0, 0.0),
                List.of(3.0, 0.0, 0.0),
                List.of(1.5, 4.0, 0.0)
        );

        triangle.setVertices(newVertices);
        assertEquals(newVertices, triangle.getVertices());
    }

    @Test
    public void testToString() {
        List<List<Double>> vertices = List.of(
                List.of(1.0, 1.0, 0.0),
                List.of(2.0, 3.0, 0.0),
                List.of(4.0, 1.0, 0.0)
        );
        Triangle triangle = new Triangle("draw", "blue", List.of(0.0), vertices);

        String expectedString = "Triangle{id=" + triangle.getId() +
                ", vertices=[[1.0, 1.0, 0.0], [2.0, 3.0, 0.0], [4.0, 1.0, 0.0]], " +
                "type=draw, shape=triangle, color=blue}";
        assertEquals(expectedString, triangle.toString());
    }
}