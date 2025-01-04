package collaborative.whiteboard.model;

import java.util.List;

/**
 * Represents a rectangle drawing, extending the functionality of the DrawingMessage class.
 *
 * @author Andrey Estevam Seabra
 */
public class Rectangle extends DrawingMessage{
    /**
     * Store the center point of the rectangle.
     */
    private List<Double> center;
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
        super.setShape("rectangle");
        // Width and height set to a standard value if none are given.
        this.width = 10;
        this.height = 10;
        this.center = List.of(0.0, 0.0, 0.0); // Rectangle will be located a the origin.
    }

    /**
     * Parameterized constructor.
     *
     * @param type the type of the drawing message (e.g., "draw").
     * @param color the color of the rectangle (e.g., "blue").
     * @param rotation the orientation of the rectangle for a 3D version; unused in the 2D version.
     * @param width the width of the rectangle.
     * @param height the height of the rectangle.
     */
    public Rectangle(String type, String color, List<Double> rotation, double width, double height, List<Double> center) {
        super(type, "rectangle", color, rotation);
        this.width = width;
        this.height = height;
        this.center = center;
    }

    /**
     * Calculates the coordinate of the top left corner of the rectangle.
     *
     * @return a list containing the coordinates.
     */
    public List<Double> getTopLeftCorner(){
        return List.of(
                (center.get(0) - width/2),
                (center.get(1) + height/2),
                center.get(2)
        );
    }

    /**
     * Calculates the coordinate of the bottom right corner of the rectangle.
     *
     * @return a list containing the coordinates.
     */
    public List<Double> getBottomRightCorner(){
        return List.of(
                (center.get(0) + width/2),
                (center.get(1) - height/2),
                center.get(2)
        );
    }

    @Override
    public String toString() {
        return "Rectangle{" +
                "id=" + getId() +
                ", type=" + getType() +
                ", shape=" + getShape() +
                ", color=" + getColor() +
                ", center=" + center +
                ", width=" + width +
                ", height=" + height +
                ", topLeftCorner=" + getTopLeftCorner() +
                ", bottomRightCorner=" + getBottomRightCorner() +
                ", rotation=" + getRotation() +
                '}';
    }

    // Getters and Setters.
    public double getWidth() {return width;}

    public void setWidth(double width) {this.width = width;}

    public double getHeight() {return height;}

    public void setHeight(double height) {this.height = height;}

    public List<Double> getCenter() {return center;}

    public void setCenter(List<Double> center) {this.center = center;}
}