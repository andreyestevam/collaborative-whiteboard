# Collaborative Whiteboard

A real-time collaborative whiteboard application designed to enable multiple users to draw, erase, and interact with a shared canvas. This project uses a full-stack architecture with a React.js frontend and a Spring Boot backend. The whiteboard supports features such as undo/redo, saving and loading states, and exporting the whiteboard as JSON, PNG, or PDF.

⚠️ **Project Status:** This project is a work in progress. Some features may not work as expected or are yet to be implemented.

---

## Features (Implemented & Planned)

### Implemented Features:
- **Real-Time Drawing:** Draw on a shared canvas using tools such as pen and eraser.
- **Undo/Redo:** Undo the last action or redo an undone action.
- **Clear Whiteboard:** Clear all strokes on the whiteboard.
- **Export Options:** Export the whiteboard as JSON, PNG, or PDF.
- **Frontend Architecture:** Built with React.js, using Konva for canvas rendering.
- **Backend API:** REST API endpoints built with Spring Boot.
- **State Management:** Manage strokes and redo/undo stacks on the frontend.

### Planned/Under Construction Features:
- **Save & Load State:** Save the current whiteboard state to the backend and reload it later.
- **Real-Time Synchronization:** Sync whiteboard updates in real-time across multiple clients.
- **Rotation (Future):** Include support for rotating shapes, though it defaults to 2D with rotation set to 0.

---

## Project Structure

### Frontend
- **Framework:** React.js
- **Canvas Library:** Konva
- **State Management:** React hooks for strokes, undo/redo stacks, and tool selection.
- **Components:**
    - `Toolbar.js`: Handles tool selection, color, and line width.
    - `Sidebar.js`: Provides options for undo/redo, save/load, and export.
    - `Whiteboard.js`: The primary canvas where drawing interactions occur.

### Backend
- **Framework:** Spring Boot
- **Endpoints:**
    - `POST /api/whiteboard/save`: Save the current whiteboard state.
    - `GET /api/whiteboard/load`: Load the saved whiteboard state.
    - `POST /api/whiteboard/undo`: Undo the last action.
    - `POST /api/whiteboard/redo`: Redo an undone action.
- **State Management:**
    - `WhiteboardState` class stores strokes as `DrawingMessage` objects.
    - Uses a `ConcurrentHashMap` to manage drawing messages.

---

## Installation

### Prerequisites
- Node.js
- Java 17+
- Maven

### Frontend Setup
1. Navigate to the `whiteboard-frontend` directory.
2. Install dependencies:
   ```bash
   npm install
   ```
3. Start the development server:
   ```bash
   npm start
   ```

### Backend Setup
1. Navigate to the `whiteboard-backend` directory.
2. Build the project with Maven:
   ```bash
   mvn clean install
   ```
3. Run the application:
   ```bash
   mvn spring-boot:run
   ```

The backend will run on `http://localhost:8080`.

---

## Usage

1. Open the frontend application at `http://localhost:3000`.
2. Use the toolbar to select tools, colors, and line widths.
3. Draw on the whiteboard using the canvas.
4. Use the sidebar to undo/redo, export, or attempt saving/loading the whiteboard state.

---

## API Endpoints

### Save State
**POST** `/api/whiteboard/save`
- **Request Body:**
  ```json
  {
    "drawingMessages": {
      "id-123": {
        "id": "id-123",
        "type": "draw",
        "shape": "line",
        "color": "black",
        "points": [0, 0, 10, 10],
        "lineWidth": 2,
        "rotation": 0
      }
    },
    "version": 1,
    "timeStamp": "2025-01-09T01:04:23.036Z"
  }
  ```

### Load State
**GET** `/api/whiteboard/load`
- **Response Body:**
  ```json
  {
    "drawingMessages": {
      "id-123": {
        "id": "id-123",
        "type": "draw",
        "shape": "line",
        "color": "black",
        "points": [0, 0, 10, 10],
        "lineWidth": 2,
        "rotation": 0
      }
    },
    "version": 1,
    "timeStamp": "2025-01-09T01:04:23.036Z"
  }
  ```

---

## Contributing

Contributions are welcome! Please open an issue or submit a pull request if you'd like to contribute.

---

## License

This project is licensed under the MIT License. See the `LICENSE` file for details.

---

## Author

**Andrey Estevam Seabra**