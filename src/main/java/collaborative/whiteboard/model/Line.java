package collaborative.whiteboard.model;

import java.util.List;

/**
 * Represents a line drawing, extending the functionality of the DrawingMessage class.
 *
 * @author Andrey Estevam Seabra
 */
public class Line extends DrawingMessage{
    private List<Double> start;
    private List<Double> end;

    public Line(){
        super();
        super.setShape("line");
        this.start = List.of(-5.0, 0.0, 0.0);
        this.end = List.of(5.0, 0.0, 0.0);
    }

    public Line(String type, String color, List<Double> rotation, List<Double> start, List<Double> end) {
        super(type, "line", color, rotation);
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString() {
        return "Line{" +
                "id=" + getId() +
                ", type=" + getType() +
                ", shape=" + getShape() +
                ", color=" + getColor() +
                ", start=" + start +
                ", end=" + end +
                ", rotation=" + getRotation() +
                '}';
    }

    // Getters and setters.
    public List<Double> getStart() {return start;}

    public void setStart(List<Double> start) {this.start = start;}

    public List<Double> getEnd() {return end;}

    public void setEnd(List<Double> end) {this.end = end;}
}