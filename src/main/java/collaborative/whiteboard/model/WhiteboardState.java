package collaborative.whiteboard.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
     * Store all the drawing messages with their IDs as keys.
     */
    private Map<String, DrawingMessage> drawingMessages;
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
    public WhiteboardState(){
        this.drawingMessages = new ConcurrentHashMap<>();
        this.timeStamp = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Timestamp(System.currentTimeMillis()));
        this.version = 1;
    }

    /**
     * Constructs a new WhiteboardState with the specified parameters.
     *
     * @param drawingMessages a ConcurrentHashMap of DrawingMessage objects representing their IDs as keys and drawing actions on the whiteboard as values.
     * @param timeStamp the timestamp indicating when the state was saved.
     * @param version the version number of this whiteboard state.
     */
    public WhiteboardState(ConcurrentHashMap<String, DrawingMessage> drawingMessages, String timeStamp, int version){
        // Initializing member variables through setter methods to handle exceptions.
        setDrawingMessages(drawingMessages);
        setTimeStamp(timeStamp);
        setVersion(version);
    }

    /**
     * Inserts a message into the drawingMessages.
     *
     * @param message the DrawingMessage to be added to the Map.
     */
    public void addDrawingMessage(DrawingMessage message){
        this.drawingMessages.put(message.getId(), message);
        incrementVersion();
    }

    /**
     * Removes a message, if it exists, from the drawingMessages.
     *
     * @param id the id from the message to be removed.
     * @return true if the message exists and was removed, false if the message did not exist.
     */
    public boolean removeDrawingMessage(String id){
        if(this.drawingMessages.containsKey(id)){
            this.drawingMessages.remove(id);
            incrementVersion();
            return true;
        }
        return false;
    }

    /**
     * Updates a specific message from the drawingMessages.
     *
     * @param message the updated message.
     * @return true if the message.getId() key exists in drawingMessages and its value was updated,
     * false if drawingMessages does not contain the message.getId() key.
     */
    public boolean updateDrawingMessage(DrawingMessage message){
        if(this.drawingMessages.containsKey(message.getId())){
            this.drawingMessages.replace(message.getId(), message);
            incrementVersion();
            return true;
        }
        return false;
    }

    /**
     * Increments the version number.
     */
    public void incrementVersion(){
        this.version++;
        timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Timestamp(System.currentTimeMillis()));
    }

    /**
     * Serializes the WhiteboardState object into JSON.
     *
     * @return A JSON-formatted string representation of the WhiteboardState object.
     * @throws IOException if an error occurs during the JSON serialization process.
     */
    public String exportToJSON() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(this);
    }

    /**
     * Deserializes a JSON string and converts it into a WhiteboardState object.
     *
     * @param json the JSON string representing the WhiteboardState.
     * @return a WhiteboardState object reconstructed from the given JSON string.
     * @throws IOException if an error occurs during JSON deserialization.
     */
    public static WhiteboardState importFromJSON(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, WhiteboardState.class);
    }

    @Override
    public String toString() {
        return "WhiteboardState{" +
                "drawingMessages=" + drawingMessages.values() +
                ", timeStamp='" + timeStamp + '\'' +
                ", version=" + version +
                '}';
    }

    // Getter and setter methods below.
    public Map<String, DrawingMessage> getDrawingMessages() {return drawingMessages;}

    public void setDrawingMessages(Map<String, DrawingMessage> drawingMessages){
        if(drawingMessages == null){
            throw new IllegalArgumentException("Drawing messages cannot be null.");
        }
        this.drawingMessages = drawingMessages;
    }

    public String getTimeStamp(){return timeStamp;}

    public void setTimeStamp(String timeStamp){
        if(timeStamp == null || !timeStamp.matches("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}")){
            throw new IllegalArgumentException("Invalid timestamp format. Expected ISO 8601 format.");
        }
        this.timeStamp = timeStamp;
    }

    public int getVersion(){return version;}

    public void setVersion(int version){
        if(version <= 0){
            throw new IllegalArgumentException("Version must be a positive integer.");
        }
        this.version = version;
    }
}