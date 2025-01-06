import './App.css';
import React, {useEffect, useState, useRef} from "react";
import Whiteboard from './components/Whiteboard';
import Toolbar from './components/Toolbar';
import Sidebar from './components/Sidebar';
import "./App.css"

/**
 * The main application component for the Collaborative Whiteboard project.
 * Manages global state, render child components, and provides functionality
 * for drawing, clearing, undoing, and redoing strokes on the whiteboard.
 *
 * @returns {Element} a React element representing the entire application,
 * including the toolbar, sidebar, and whiteboard canvas.
 *
 * @author Andrey Estevam Seabra
 */
const App = () => {
    // Global state management set to pen, black, width 2 and empty array of strokes.
    const [tool, setTool] = useState("pen");
    const [color, setColor] = useState("black");
    const [lineWidth, setLineWidth] = useState(2);
    const [strokes, setStrokes] = useState([]);
    const [redoStack, setRedoStack] = useState([]); // Stack for redo functionality.

    const stageRef = useRef(null);

    /**
     * Handle undoing the last stroke. Moves the last stroke from the strokes array to the redo stack.
     */
    const undo = () => {
        if(strokes.length > 0){
            const lastStroke = strokes[strokes.length-1];
            setRedoStack([...redoStack, lastStroke]); // Add the last stroke to the redo stack.
            setStrokes(strokes.slice(0, -1)); // Remove the last stroke.
        }
    }

    /**
     * Handle redoing the last undone stroke. Moves the last stroke from the redo stack back to the strokes array.
     */
    const redo = () => {
        if(redoStack.length > 0){
            const lastRedoStroke = redoStack[redoStack.length-1];
            setStrokes([...strokes, lastRedoStroke]) // Add the last undone stroke back to strokes.
            setRedoStack(redoStack.slice(0, -1)); // Remove the stroke from redo stack.
        }
    }

    /**
     * Clear the whiteboard. Clears both the strokes array and the redo stack.
     */
    const clearWhiteboard = () => {
        setStrokes([]); // Clear all strokes.
        setRedoStack([]); // Clear redo stack.
    }

    /**
     * Adds user functionality by undoing and redoing strokes with keyboard shortcuts.
     * For undoing, press Ctrl+Z or Cmd+Z.
     * For redoing, press Ctrl+Shift+Z or Cmd+Shift+Z
     */
    useEffect(() => {
        const handleKeyDown = (e) => {
            if((e.ctrlKey || e.metaKey) && e.key  === "z"){
                if(e.shiftKey){
                    redo(); // Ctrl+Shift+Z or Cmd+Shift+Z
                }else{
                    undo(); // Ctrl+Z or Cmd+Z
                }
            }
        };

        window.addEventListener("keydown", handleKeyDown);
        return () => {
            window.removeEventListener("keydown", handleKeyDown);
        };
    }, [undo, redo]);

    return (
        <div className="app-container">
            {/*Toolbar.*/}
            <Toolbar
            setTool={setTool}
            setColor={setColor}
            setLineWidth={setLineWidth}
            clearWhiteboard={clearWhiteboard}
            />

            {/*Main content: sidebar and whiteboard.*/}
            <div className="main-content">
                <Sidebar
                    undo={undo}
                    redo={redo}
                    strokes={strokes}
                    setStrokes={setStrokes}
                    stageRef={stageRef}
                />
                <Whiteboard
                    tool={tool}
                    color={color}
                    lineWidth={lineWidth}
                    strokes={strokes}
                    setStrokes={setStrokes}
                    redoStack={redoStack}
                    setRedoStack={setRedoStack}
                    stageRef={stageRef}
                />
            </div>
        </div>
  );
}

export default App;