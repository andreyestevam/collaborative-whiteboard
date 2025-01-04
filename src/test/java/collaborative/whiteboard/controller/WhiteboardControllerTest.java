package collaborative.whiteboard.controller;

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
        String jsonPayload = "{\"drawingMessages\": [], \"timeStamp\": \"2025-01-02T10:15:30\", \"version\": 1}";
        mockMvc.perform(post("/api/whiteboard/save").contentType("application/json")
                .content(jsonPayload)).andExpect(status().isOk());

        mockMvc.perform(get("/api/whiteboard/load")).andExpect(status().isOk())
                .andExpect(jsonPath("$.timeStamp").value("2025-01-02T10:15:30"));
    }
}