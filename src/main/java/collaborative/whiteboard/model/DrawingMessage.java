package collaborative.whiteboard.model;

import java.util.List;

public class DrawingMessage{
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
     * Store the coordinates of the start point of the message (e.g. [x,y,z]).
     * z-coordinate is set to 0 for the 2D version.
     */
    private List<Integer> start;
    /**
     * Store the coordinates of the end point of the message (e.g. [x,y,z]).
     * z-coordinate is set to 0 for the 2D version.
     */
    private List<Integer> end;
    /**
     * Store the orientation of the object. Not currently used for the 2D version (will be implemented for the 3D version).
     */
    private List<Integer> rotation; // Not currently used, but will be implemented for the 3D version.

    /**
     * Default constructor, required for Jackson.
     */
    public DrawingMessage(){
    }

    /**
     * Constructs a new DrawingMessage with specified parameters.
     *
     * @param type the type of the message.
     * @param shape the shape to be drawn.
     * @param color the color of the shape.
     * @param start the coordinates of the starting point of the shape.
     * @param end the coordinates of the ending point of the shape.
     * @param rotation the orientation of the object for a 3D version; unused in the 2D version.
     */
    public DrawingMessage(String type, String shape, String color, List<Integer> start, List<Integer> end, List<Integer> rotation){
        // Initializes all the member variables.
        this.type = type;
        this.shape = shape;
        this.color = color;
        this.start = start;
        this.end = end;
        this.rotation = rotation;
    }

    // Getter and setter methods.
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

    public List<Integer> getStart(){
        return start;
    }

    public void setStart(List<Integer> start){
        this.start = start;
    }

    public List<Integer> getEnd(){
        return end;
    }

    public void setEnd(List<Integer> end){
        this.end = end;
    }

    public List<Integer> getRotation(){
        return rotation;
    }

    public void setRotation(List<Integer> rotation){
        this.rotation = rotation;
    }
}
