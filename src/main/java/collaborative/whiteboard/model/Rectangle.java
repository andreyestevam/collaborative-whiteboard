package collaborative.whiteboard.model;

import java.util.List;

/**
 * Represents a rectangle drawing, extending the functionality of the DrawingMessage class.
 *
 * @author Andrey Estevam Seabra
 */
public class Rectangle extends DrawingMessage{
    /**
     * Store the width of the rectangle drawing.
     */
    private double width;
    /**
     * Store the height of the rectangle drawing.
     */
    private double height;

    /**
     * Default constructor.
     */
    public Rectangle(){
        super();
        this.width = 0;
        this.height = 0;
    }

    /**
     * Parameterized constructor.
     *
     * @param type the type of the drawing message (e.g., "draw").
     * @param shape the shape to be drawn (e.g., "rectangle").
     * @param color the color of the rectangle (e.g., "blue").
     * @param start the coordinates of the starting point of the rectangle.
     * @param end the coordinates of the ending point of the rectangle.
     * @param rotation the orientation of the rectangle for a 3D version; unused in the 2D version.
     * @param width the width of the rectangle.
     * @param height the height of the rectangle.
     */
    public Rectangle(String type, String shape, String color, List<Integer> start, List<Integer> end, List<Integer> rotation, double width, double height) {
        super(type, shape, color, start, end, rotation);
        this.width = width;
        this.height = height;
    }

    @Override
    public String toString() {
        return "Rectangle{" +
                "id=" + getId() +
                ", width=" + width +
                ", height=" + height +
                ", type=" + getType() +
                ", shape=" + getShape() +
                ", color=" + getColor() +
                ", start=" + getStart() +
                ", end=" + getEnd() +
                ", rotation=" + getRotation() +
                '}';
    }

    // Getters and setters below.
    public double getWidth() {return width;}

    public void setWidth(double width) {this.width = width;}

    public double getHeight() {return height;}

    public void setHeight(double height) {this.height = height;}
}