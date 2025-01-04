package collaborative.whiteboard.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Represents the state of the whiteboard, including its drawings, timestamp,
 * and version information. This class is primarily used to persist and retrieve
 * the current state of a collaborative whiteboard.
 *
 * @author Andrey Estevam Seabra
 */
@SpringBootApplication
public class WhiteboardState {
    /**
     * List of all the drawing actions on the whiteboard.
     */
    private List<DrawingMessage> drawingMessages;
    /**
     * Time the state was saved.
     */
    private String timeStamp;
    /**
     * Represents the version of the whiteboard state.
     */
    private int version;

    /**
     * Default constructor required for JSON deserialization.
     */
    public WhiteboardState(){}

    /**
     * Constructs a new WhiteboardState with the specified parameters.
     *
     * @param drawingMessages a list of DrawingMessage objects representing the drawing actions on the whiteboard.
     * @param timeStamp the timestamp indicating when the state was saved.
     * @param version the version number of this whiteboard state.
     */
    public WhiteboardState(List<DrawingMessage> drawingMessages, String timeStamp, int version){
        this.drawingMessages = drawingMessages;
        this.timeStamp = timeStamp;
        this.version = version;
    }

    @Override
    public String toString() {
        return "WhiteboardState{" +
                "drawingMessages=" + drawingMessages +
                ", timeStamp=" + timeStamp +
                ", version=" + version +
                "}";
    }

    // Getter and setter methods below.
    public List<DrawingMessage> getDrawingMessages() {return drawingMessages;}

    public void setDrawingMessages(List<DrawingMessage> drawingMessages){this.drawingMessages = drawingMessages;}

    public String getTimeStamp(){return timeStamp;}

    public void setTimeStamp(String timeStamp){this.timeStamp = timeStamp;}

    public int getVersion(){return version;}

    public void setVersion(int version){this.version = version;}
}