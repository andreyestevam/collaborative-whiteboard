package collaborative.whiteboard.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = collaborative.whiteboard.collaborative_whiteboard.CollaborativeWhiteboardApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebSocketConfigTest {

    @Autowired
    private WebApplicationContext context;
    private static final String WEB_SOCKET_ENDPOINT = "ws://localhost:8080/whiteboard";

    @Test
    void shouldLoadWebSocketConfig() {
        assertThat(context).isNotNull();
        assertThat(context.containsBean("webSocketConfig")).isTrue();
    }
}