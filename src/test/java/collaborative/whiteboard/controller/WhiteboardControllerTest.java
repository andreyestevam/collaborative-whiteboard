package collaborative.whiteboard.controller;

import collaborative.whiteboard.manager.StateManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for WhiteboardController.
 *
 * @author Andrey Estevam Seabra
 */
@SpringBootTest
@AutoConfigureMockMvc
public class WhiteboardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldSaveAndLoadWhiteboardState() throws Exception {
        String jsonPayload = "{\"drawingMessages\": {}, \"timeStamp\": \"2025-01-02T10:15:30\", \"version\": 1}";
        mockMvc.perform(post("/api/whiteboard/save").contentType("application/json")
                .content(jsonPayload)).andExpect(status().isOk());

        mockMvc.perform(get("/api/whiteboard/load")).andExpect(status().isOk())
                .andExpect(jsonPath("$.timeStamp").value("2025-01-02T10:15:30"));
    }

    @Test
    public void shouldNotSaveStateWithInvalidTimestamp() throws Exception {
        String invalidPayload = "{\"drawingMessages\": {}, \"timeStamp\": \"\", \"version\": 1}";
        mockMvc.perform(post("/api/whiteboard/save").contentType("application/json")
                        .content(invalidPayload))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid timestamp format. Expected ISO 8601 format."));
    }

    @Test
    public void shouldNotSaveStateWithInvalidVersion() throws Exception {
        String invalidPayload = "{\"drawingMessages\": {}, \"timeStamp\": \"2025-01-02T10:15:30\", \"version\": 0}";
        mockMvc.perform(post("/api/whiteboard/save").contentType("application/json")
                        .content(invalidPayload))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Version must be a positive integer."));
    }

    @Test
    public void shouldUndoStateSuccessfully() throws Exception {
        String initialState = "{\"drawingMessages\": {}, \"timeStamp\": \"2025-01-02T10:15:30\", \"version\": 1}";
        String updatedState = "{\"drawingMessages\": {}, \"timeStamp\": \"2025-01-02T10:16:00\", \"version\": 2}";

        // Save initial state
        mockMvc.perform(post("/api/whiteboard/save").contentType("application/json")
                .content(initialState)).andExpect(status().isOk());

        // Save updated state
        mockMvc.perform(post("/api/whiteboard/save").contentType("application/json")
                .content(updatedState)).andExpect(status().isOk());

        // Perform undo
        mockMvc.perform(post("/api/whiteboard/undo")).andExpect(status().isOk())
                .andExpect(content().string("Undo successful."));

        // Verify the reverted state
        mockMvc.perform(get("/api/whiteboard/currentState"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.timeStamp").value("2025-01-02T10:15:30"));
    }

    @Test
    public void shouldRedoStateSuccessfully() throws Exception {
        String initialState = "{\"drawingMessages\": {}, \"timeStamp\": \"2025-01-02T10:15:30\", \"version\": 1}";
        String updatedState = "{\"drawingMessages\": {}, \"timeStamp\": \"2025-01-02T10:16:00\", \"version\": 2}";

        // Save initial state
        mockMvc.perform(post("/api/whiteboard/save").contentType("application/json")
                .content(initialState)).andExpect(status().isOk());

        // Save updated state
        mockMvc.perform(post("/api/whiteboard/save").contentType("application/json")
                .content(updatedState)).andExpect(status().isOk());

        // Perform undo
        mockMvc.perform(post("/api/whiteboard/undo")).andExpect(status().isOk());

        // Perform redo
        mockMvc.perform(post("/api/whiteboard/redo")).andExpect(status().isOk())
                .andExpect(content().string("Redo successful."));

        // Verify the restored state
        mockMvc.perform(get("/api/whiteboard/currentState"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.timeStamp").value("2025-01-02T10:16:00"));
    }

    @Test
    public void shouldReturnCurrentState() throws Exception {
        String jsonPayload = "{\"drawingMessages\": {}, \"timeStamp\": \"2025-01-02T10:15:30\", \"version\": 1}";
        mockMvc.perform(post("/api/whiteboard/save").contentType("application/json")
                .content(jsonPayload)).andExpect(status().isOk());

        mockMvc.perform(get("/api/whiteboard/currentState"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.timeStamp").value("2025-01-02T10:15:30"));
    }
}