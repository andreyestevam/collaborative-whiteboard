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
     * Store the coordinates of the center of the circle.
     */
    private List<Double> center;

    /**
     * Default constructor for the Circle class.
     */
    public Circle(){
        super();
        super.setShape("circle");
        this.radius = 0.0;
        center = List.of(0.0, 0.0, 0.0);
    }
    /**
     * Creates a new instance of the Circle class, inheriting properties from the DrawingMessage class
     * and adding specific functionality for representing a circle with a given radius.
     *
     * @param type the type of the drawing message (e.g., "draw").
     * @param color the color of the circle (e.g., "red").
     * @param rotation the orientation of the circle for a 3D version; unused in the 2D version.
     * @param radius the radius of the circle.
     */
    public Circle(String type, String color, List<Double> rotation, double radius, List<Double> center) {
        super(type, "circle", color, rotation);
        this.radius = radius;
        this.center = center;
    }

    @Override
    public String toString() {
        return "Circle{" +
                "id=" + getId() +
                ", type=" + getType() +
                ", shape=" + getShape() +
                ", color=" + getColor() +
                ", center=" + center +
                ", radius=" + radius +
                ", rotation=" + getRotation() +
                '}';
    }

    // Getter and setter methods.
    public double getRadius() {return radius;}

    public void setRadius(double radius) {this.radius = radius;}

    public List<Double> getCenter() {return center;}

    public void setCenter(List<Double> center) {this.center = center;}
}