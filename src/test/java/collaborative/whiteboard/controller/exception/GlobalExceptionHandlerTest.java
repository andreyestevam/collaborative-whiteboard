package collaborative.whiteboard.controller.exception;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturn400ForInvalidJsonPayload() throws Exception {
        String invalidJson = "{\"drawingMessages\": [], \"timeStamp\": \"\", \"version\": -1}";
        mockMvc.perform(post("/api/whiteboard/save").contentType("application/json").content(invalidJson))
                .andExpect(status().isBadRequest());
    }


}
