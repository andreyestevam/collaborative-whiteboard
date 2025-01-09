import SockJS from "sockjs-client";
import { Stomp } from "@stomp/stompjs";

let client = null;

/**
 * Connects to the WebSocket server and subscribes to the topic for receiving updates.
 *
 * @param onMessageReceived (function) callback function to handle received messages.
 */
export const connectWebSocket = (onMessageReceived) => {
    client = Stomp.over(() => new SockJS("http://localhost:8080/whiteboard"));

    client.debug = () => {};

    client.connect({}, () => {
        console.log("WebSocket connected");

        // Subscribe to the topic for receiving updates
        client.subscribe("/topic/drawings", (message) => {
            try {
                const data = JSON.parse(message.body); // Parse the received message
                onMessageReceived(data); // Call the provided callback with the received data
            } catch (error) {
                console.error("Failed to parse WebSocket message:", error);
            }
        });
    }, (error) => {
        console.error("WebSocket connection error:", error);
        setTimeout(() => connectWebSocket(onMessageReceived), 5000); // Retry after 5 seconds.
    });
};

/**
 * Sends a drawing message to the backend.
 *
 * @param drawing (object) the drawing data to be sent.
 */
export const sendDrawingMessage = (drawing) => {
    if (client && client.connected) {
        client.publish({
            destination: "/app/draw",
            body: JSON.stringify(drawing),
        });
        console.log("Drawing message send:", drawing);
    } else {
        console.error("WebSocket client is not connected.");
    }
};
