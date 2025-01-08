import SockJS from "sockjs-client";
import { Stomp } from "@stomp/stompjs";

let client = null;

export const connectWebSocket = (onMessageReceived) => {
    client = Stomp.over(() => new SockJS("http://localhost:8080/whiteboard")); // Replace with your backend URL

    client.connect({}, () => {
        console.log("WebSocket connected");

        // Subscribe to the topic for receiving updates
        client.subscribe("/topic/drawings", (message) => {
            const data = JSON.parse(message.body); // Parse the received message
            onMessageReceived(data); // Call the provided callback with the received data
        });
    });

    client.debug = null; // Disable verbose debug logging
};

export const sendDrawingMessage = (drawing) => {
    if (client && client.connected) {
        client.publish({
            destination: "/app/draw",
            body: JSON.stringify(drawing),
        });
    } else {
        console.error("WebSocket client is not connected.");
    }
};
