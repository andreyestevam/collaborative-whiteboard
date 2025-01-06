import React, {useState, forwardRef} from 'react';
import '../styles/Whiteboard.css';
import {Stage, Layer, Line} from "konva";

/**
 * Whiteboard component. Renders an interactive whiteboard for users to draw on.
 * Uses Konva to handle the canvas and drawing functionality.
 * Users can draw using tools like pen and eraser, customize their stroke color and width,
 * and interact with previously drawn strokes using undo/redo features.
 *
 * @param tool (string) the current drawing tool (e.g. "pen", "eraser").
 * @param color (string) the color of the current stroke.
 * @param lineWidth (number) the width of the current stroke.
 * @param strokes (array) all the completed strokes. Each stroke is an object.
 * @param setStrokes (function) update the array of completed strokes.
 * @param redoStack (array) stack of undone strokes available for redo.
 * @param setRedoStack (function) update the redo stack.
 * @param stageRef (React ref) reference to the Konva `Stage` instance.
 * @returns {Element} a React element representing the canvas and the drawing area.
 *
 * @author Andrey Estevam Seabra
 */
const Whiteboard = React.forwardRef(({tool, color, lineWidth, strokes, setStrokes, redoStack, setRedoStack}, ref) => {
    // Store the current stroke being drawn. Updated whenever the user moves the mouse.
    const [currentStroke, setCurrentStroke] = useState([]);

    // Handle the start of a new stroke.
    const handleMouseDown = (e) => {
        const stage = e.target.getStage();
        const point = stage.getPointerPosition();
        setCurrentStroke([{tool, color, lineWidth, points: [point.x, point.y]}]);
    };

    // Handle the drawing.
    const handleMouseMove = (e) => {
        // Do nothing whenever the user is not drawing (mouse not pressed).
        if(!currentStroke.length) return;

        const stage = e.target.getStage();
        const point = stage.getPointerPosition();

        // Update the current stroke by adding the new point to points array.
        const newStroke = [...currentStroke];
        newStroke[0].points = [...newStroke[0].points, point.x, point.y];
        setCurrentStroke(newStroke);
    };

    // Handle the end of a stroke.
    const handleMouseUp = () => {
        if(currentStroke.length > 0){
            setStrokes((prevStrokes) => {
                setRedoStack([]); // Clear the redo stack whenever a new stroke is added.
                return [...prevStrokes, ...currentStroke]; // Add the new stroke to strokes.
            });
            setCurrentStroke([]);
        }
    };

    return(
        <Stage
            width={window.innerWidth}
            height={window.innerHeight}
            onMouseDown={handleMouseDown}
            onMouseMove={handleMouseMove}
            onMouseUp={handleMouseUp}
            ref={ref}
        >
            <Layer>
                {/*Render all completed strokes.*/}
                {strokes.map((stroke, index) => (
                    <Line
                    key={index}
                    points={stroke.points}
                    stroke={stroke.color}
                    strokeWidth={stroke.lineWidth}
                    lineCap="round"
                    lineJoin="round"
                    />
                ))}

                {/*Render the current stroke being drawn.*/}
                {currentStroke.map((stroke, index) => (
                    <Line
                    key={`current-${index}`}
                    points={stroke.points}
                    stroke={stroke.color}
                    strokeWidth={stroke.lineWidth}
                    lineCap="round"
                    lineJoin="round"
                    />
                ))}
            </Layer>
        </Stage>
    );
});

export default Whiteboard;