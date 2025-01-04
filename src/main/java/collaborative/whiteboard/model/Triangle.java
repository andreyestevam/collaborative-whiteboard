package collaborative.whiteboard.model;

import java.util.List;

/**
 * Represents a Triangle drawing, extending the functionality of the DrawingMessage class.
 *
 * @author Andrey Estevam Seabra
 */
public class Triangle extends DrawingMessage{
    /**
     * Store the vertices of the triangle. Each vertice is a List of [x,y,z].
     */
    private List<List<Double>> vertices;

    /**
     * Default constructor.
     */
    public Triangle(){
        super();
        super.setShape("triangle");
        this.vertices = List.of(
                List.of(-2.0, -2.0, 0.0),
                List.of(2.0, -2.0, 0.0),
                List.of(0.0, 2.0, 0.0)
        );
    }

    /**
     * Parameterized constructor.
     *
     * @param type the type of the drawing message (e.g., "draw").
     * @param color the color of the triangle (e.g., "green").
     * @param rotation the orientation of the triangle for a 3D version; unused in the 2D version.
     * @param vertices the list of vertex points representing the triangle. Each vertex is a list of [x, y, z] coordinates.
     */
    public Triangle(String type, String color, List<Double> rotation, List<List<Double>> vertices) {
        super(type, "triangle", color, rotation);
        this.vertices = vertices;
    }

    @Override
    public String toString() {
        return "Triangle{" +
                "id=" + getId() +
                ", vertices=" + vertices +
                ", type=" + getType() +
                ", shape=" + getShape() +
                ", color=" + getColor() +
                '}';
    }

    // Getter and setter methods.
    public List<List<Double>> getVertices() {return vertices;}

    public void setVertices(List<List<Double>> vertices) {this.vertices = vertices;}
}