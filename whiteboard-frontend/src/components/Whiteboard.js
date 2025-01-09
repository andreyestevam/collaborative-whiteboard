import React, {useEffect, useRef, useState} from 'react';
import '../styles/Whiteboard.css';
import Konva from "konva";
import {sendDrawingMessage} from "../utils/websocket";
import {generateUniqueID} from "../utils/idGenerator";

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
    const containerRef = useRef(null);
    const [stage, setStage] = useState(null);
    const [currentLine, setCurrentLine] = useState(null);
    let isDrawing = useRef(true);

    useEffect(() => {
        const stageInstance = new Konva.Stage({
            container: containerRef.current,
            width: window.innerWidth,
            height: window.innerHeight,
        });

        const layer = new Konva.Layer();
        stageInstance.add(layer);

        setStage(stageInstance);

        if(ref){
            ref.current = {
                clearCanvas: () => {
                    layer.destroyChildren();
                    layer.batchDraw();
                },
            };
        }

        return() => {
            stageInstance.destroy();
        };
    }, [ref]);

    const createDrawingMessage = (line) => {
        return{
            id: generateUniqueID(),
            type: "draw",
            shape: "line",
            color: line.stroke(),
            points: line.points(),
            lineWidth: line.strokeWidth(),
            rotation: 0, // Always 0 for 2D drawing.
        }
    }

    // Debugging tool, color and lineWidth values.
    useEffect(() => {
        console.log('Tool:', tool, 'Color:', color, 'LineWidth:', lineWidth);
    }, [tool, color, lineWidth]);

    // Redraw strokes whenever `strokes` changes
    useEffect(() => {
        if (!stage) return;

        const layer = stage.findOne("Layer");
        layer.destroyChildren(); // Clear existing shapes

        strokes.forEach((stroke) => {
            const line = new Konva.Line({
                stroke: stroke.color,
                strokeWidth: stroke.lineWidth,
                lineCap: "round",
                lineJoin: "round",
                points: stroke.points,
            });
            layer.add(line);
        });

        layer.batchDraw();
    }, [strokes, stage]);

    useEffect(() => {
        if(!stage) return;

        const layer = stage.findOne("Layer");

        const handleMouseDown = (e) => {
            console.log("Mouse down at:", stage.getPointerPosition());
            e.evt.preventDefault();

            isDrawing.current = true;

            const pos = stage.getPointerPosition();
            const line = new Konva.Line({
                stroke: tool === "eraser" ? "#f0f0f0" : color,
                strokeWidth: parseFloat(lineWidth) || 2,
                lineCap: "round",
                lineJoin: "round",
                points: [pos.x, pos.y],
            });

            setCurrentLine(line);
            layer.add(line);
        };

        const handleMouseMove = () => {
            console.log("Mouse move at:", stage.getPointerPosition());
            if(!isDrawing.current || !currentLine) return;

            const pos = stage.getPointerPosition();
            const newPoints = [...currentLine.points(), pos.x, pos.y];
            currentLine.points(newPoints);

            layer.batchDraw();
        };

        const handleMouseUp = () => {
            console.log("Mouse up");
            if (!isDrawing.current) return;

            isDrawing.current = false;

            if(currentLine){
                const newStroke = createDrawingMessage(currentLine);
                setStrokes((prevStrokes) => {
                    setRedoStack([]);
                    return [...prevStrokes, newStroke];
                });

                sendDrawingMessage(newStroke);
                setCurrentLine(null);
            }
        };

        // Attach Konva stage events.
        stage.on("mousedown", handleMouseDown);
        stage.on("mousemove", handleMouseMove);
        stage.on("mouseup", handleMouseUp);

        return () => {
            stage.off("mousedown", handleMouseDown);
            stage.off("mousemove", handleMouseMove);
            stage.off("mouseup", handleMouseUp);
        }
    }, [stage, tool, color, lineWidth, setStrokes, setRedoStack, currentLine]);

    return <div
        ref={containerRef}
        style={{
            width: "100%",
            height: "100vh",
            background: "#f0f0f0",
            cursor: tool === "eraser" ? "not-allowed" : "crosshair",
        }}
    />;
});

export default Whiteboard;