package collaborative.whiteboard.model;

import java.util.List;

/**
 * Represents a circle drawing, extending the functionality of DrawingMessage.
 *
 * @author Andrey Estevam Seabra
 */
public class Circle extends DrawingMessage{
    /**
     * Store the radius of the Circle message.
     */
    private double radius;

    /**
     * Default constructor for the Circle class.
     */
    public Circle(){
        super();
        this.radius = 0.0;
    }
    /**
     * Creates a new instance of the Circle class, inheriting properties from the DrawingMessage class
     * and adding specific functionality for representing a circle with a given radius.
     *
     * @param type the type of the drawing message (e.g., "draw").
     * @param shape the shape to be drawn (e.g., "circle").
     * @param color the color of the circle (e.g., "red").
     * @param start the coordinates of the starting point of the circle.
     * @param end the coordinates of the ending point of the circle.
     * @param rotation the orientation of the circle for a 3D version; unused in the 2D version.
     * @param radius the radius of the circle.
     */
    public Circle(String type, String shape, String color, List<Integer> start, List<Integer> end, List<Integer> rotation, double radius) {
        super(type, shape, color, start, end, rotation);
        this.radius = radius;
    }

    @Override
    public String toString() {
        return "Circle{" +
                "id=" + getId() +
                ", radius=" + radius +
                ", type=" + getType() +
                ", shape=" + getShape() +
                ", color=" + getColor() +
                ", start=" + getStart() +
                ", end=" + getEnd() +
                ", rotation=" + getRotation() +
                '}';
    }

    // Getter and setter methods.
    public double getRadius() {return radius;}

    public void setRadius(double radius) {this.radius = radius;}
}