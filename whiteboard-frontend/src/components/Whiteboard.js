import React, {useState} from 'react';
import '../styles/Whiteboard.css';
import Konva, {Stage, Layer, Line} from "konva";

const Whiteboard = () => {
    // Store completed strokes (each stroke is an object with properties like tool, color, lineWidth, and points)
    const [strokes, setStrokes] = useState([]);

    // Store the current stroke being drawn. Updated whenever the user moves the mouse.
    const [currentStroke, setCurrentStroke] = useState([]);

    // Store the selected drawing tool.
    const [tool, setTool] = useState('pen');

    // Store the color for the stroke.
    const [color, setColor] = useState('black');

    // Store the line width of the stroke.
    const [lineWidth, setLineWidth] = useState(2);

    // Handle the start of a new stroke.
    const handleMouseDown = (e) => {
        const stage = e.target.getStage();
        const point = stage.getPointerPosition();
        setCurrentStroke([{tool, color, lineWidth, points: [point.x, point.y]}]);
    }

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
    }

    // Handle the end of a stroke.
    const handleMouseUp = (e) => {
        // Add the completed stroke to the strokes array and clear the current stroke.
        setStrokes([...strokes, ...currentStroke]);
        setCurrentStroke([]);
    }

    return(
        <Stage
            width={window.innerWidth}
            height={window.innerHeight}
            onMouseDown={handleMouseDown}
            onMouseMove={handleMouseMove}
            onMouseUp={handleMouseUp}
        >
            <Layer>
                {/*Render all completed strokes.*/}
                {strokes.map((stroke, index) => (
                    <Line
                    key={index}
                    points={stroke.points}
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
};

export default Whiteboard;