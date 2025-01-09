package collaborative.whiteboard.model;

import java.util.List;
import java.util.UUID;

/**
 * Represents a message that specifies drawing instructions, including the type, shape,
 * color, starting coordinates, ending coordinates, and rotation for a given drawing operation.
 * This class is primarily designed to handle drawing-related data in both 2D and 3D contexts.
 *
 * @author Andrey Estevam Seabra
 */
public class DrawingMessage{
    /**
     * Store the id of the message, ensuring each message can be uniquely searched.
     */
    private final String id;
    /**
     * Store the type of message (e.g. "draw").
     */
    private String type;
    /**
     * Store the shape (e.g. "circle" or "line")
     */
    private String shape;
    /**
     * Store the color (e.g. "yellow")
     */
    private String color;
    /**
     * Store the array of numbers for coordinates.
     */
    private List<Double> points;
    /**
     * Store the line width of the drawing.
     */
    private double lineWidth;
    /**
     * Store the orientation of the object. Not currently used for the 2D version (will be implemented for the 3D version).
     */
    private List<Double> rotation; // Not currently used, but will be implemented for the 3D version.

    /**
     * Default constructor, required for Jackson.
     */
    public DrawingMessage(){
        this.id = UUID.randomUUID().toString();
    }

    /**
     * Constructs a new DrawingMessage with specified parameters.
     *
     * @param type the type of the message.
     * @param shape the shape to be drawn.
     * @param color the color of the shape.
     * @param rotation the orientation of the object for a 3D version; unused in the 2D version.
     */
    public DrawingMessage(String type, String shape, String color, List<Double> rotation){
        // Initializes all the member variables.
        this.id = UUID.randomUUID().toString();
        this.type = type;
        this.shape = shape;
        this.color = color;
        this.rotation = rotation;
    }

    @Override
    public String toString() {
        return "DrawingMessage{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", shape='" + shape + '\'' +
                ", color='" + color + '\'' +
                ", points=" + points +
                ", lineWidth=" + lineWidth +
                ", rotation=" + rotation +
                '}';
    }

    // Getter and setter methods.
    public String getId() {return id;}

    public String getType(){
        return type;
    }

    public void setType(String type){
        this.type = type;
    }

    public String getShape(){
        return shape;
    }

    public void setShape(String shape){
        this.shape = shape;
    }

    public String getColor(){
        return color;
    }

    public void setColor(String color){
        this.color = color;
    }

    public List<Double> getPoints(List<Double> points){return points;}

    public void setPoints(List<Double> points){this.points = points;}

    public double getLineWidth(){return lineWidth;}

    public void setLineWidth(double lineWidth){this.lineWidth = lineWidth;}

    public List<Double> getRotation(){
        return rotation;
    }

    public void setRotation(List<Double> rotation){
        this.rotation = rotation;
    }
}
