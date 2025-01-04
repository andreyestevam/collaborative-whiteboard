package collaborative.whiteboard.model;

import java.util.List;


public class Triangle extends DrawingMessage{
    /**
     * Store the vertices of the triangle. Each vertice is a List of [x,y,z].
     */
    private List<List<Integer>> vertices;

    /**
     * Default constructor.
     */
    public Triangle(){
        super();
        this.vertices = null;
    }

    /**
     * Parameterized constructor.
     *
     * @param type the type of the drawing message (e.g., "draw").
     * @param shape the shape to be drawn (e.g., "triangle").
     * @param color the color of the triangle (e.g., "green").
     * @param start the coordinates of the starting point of the triangle.
     * @param end the coordinates of the ending point of the triangle.
     * @param rotation the orientation of the triangle for a 3D version; unused in the 2D version.
     * @param vertices the list of vertex points representing the triangle. Each vertex is a list of [x, y, z] coordinates.
     */
    public Triangle(String type, String shape, String color, List<Integer> start, List<Integer> end, List<Integer> rotation, List<List<Integer>> vertices) {
        super(type, shape, color, start, end, rotation);
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
    public List<List<Integer>> getVertices() {return vertices;}

    public void setVertices(List<List<Integer>> vertices) {this.vertices = vertices;}
}