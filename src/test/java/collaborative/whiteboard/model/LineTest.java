package collaborative.whiteboard.model;

import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Line message.
 *
 * @author Andrey Estevam Seabra
 */
public class LineTest {
    @Test
    public void testDefaultConstructor() {
        Line line = new Line();

        assertEquals("line", line.getShape());
        assertEquals(List.of(-5.0, 0.0, 0.0), line.getStart());
        assertEquals(List.of(5.0, 0.0, 0.0), line.getEnd());
        assertNotNull(line.getId());
    }

    @Test
    public void testParameterizedConstructor() {
        List<Double> start = List.of(0.0, 0.0, 0.0);
        List<Double> end = List.of(10.0, 10.0, 0.0);
        Line line = new Line("draw", "red", List.of(0.0), start, end);

        assertEquals("draw", line.getType());
        assertEquals("line", line.getShape());
        assertEquals("red", line.getColor());
        assertEquals(start, line.getStart());
        assertEquals(end, line.getEnd());
        assertNotNull(line.getId());
    }

    @Test
    public void testSetStartAndEnd() {
        Line line = new Line();

        List<Double> newStart = List.of(-10.0, 0.0, 0.0);
        List<Double> newEnd = List.of(10.0, 0.0, 0.0);

        line.setStart(newStart);
        line.setEnd(newEnd);

        assertEquals(newStart, line.getStart());
        assertEquals(newEnd, line.getEnd());
    }

    @Test
    public void testToString() {
        List<Double> start = List.of(1.0, 1.0, 0.0);
        List<Double> end = List.of(3.0, 3.0, 0.0);
        Line line = new Line("draw", "blue", List.of(0.0), start, end);

        String expectedString = "Line{" +
                "id=" + line.getId() +
                ", type=" + line.getType() +
                ", shape=" + line.getShape() +
                ", color=" + line.getColor() +
                ", start=" + start +
                ", end=" + end +
                ", rotation=" + line.getRotation() +
                '}';

        assertEquals(expectedString, line.toString());
    }
}