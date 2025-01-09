import React, {useState} from "react";
import {jsPDF} from 'jspdf';

/**
 * Sidebar Component.
 *
 * Provides the user interface for managing whiteboard exports and undo/redo functionality.
 * Includes a dropdown menu with options for exporting the whiteboard content in various
 * formats (JSON, PDF, PNG) and buttons for undoing and redoing strokes on the whiteboard.
 *
 * @param undo (function) undo the last stroke from the whiteboard.
 * @param redo (function) redo the last undone stroke.
 * @param strokes (array) the current array of strokes drawn on the whiteboard.
 * @param stageRef (React ref) reference to the Konva `Stage` instance.
 * @param setStrokes (function) state updater function for the `strokes` array.
 * @returns {Element} a React element rendering the sidebar with export and undo/redo functionality.
 * @author Andrey Estevam Seabra
 */
const Sidebar = ({undo, redo, strokes, stageRef, setStrokes}) => {
    // State to toggle dropdown visibility
    const [dropdownVisible, setDropdownVisible] = useState(false);
    let version = 1;

    // Toggle dropdown visibility
    const toggleDropdown = () => {
        setDropdownVisible(!dropdownVisible);
    };

    /**
     * Save the current whiteboard state to the backend.
     */
    const saveState = async () => {
        try {
            const drawingMessages = strokes.reduce((acc, stroke) => {
                acc[stroke.id] = {
                    id: stroke.id,
                    type: stroke.type || "draw",
                    shape: stroke.shape || "line",
                    color: stroke.color || "black",
                    rotation: stroke.rotation || 0, // Default to 0
                    lineWidth: stroke.lineWidth || 2,
                    points: stroke.points || [],
                };
                return acc;
            }, {});

            const payload = {
                drawingMessages: drawingMessages, // Now an object, not an array
                version: version++,
                timeStamp: new Date().toISOString(),
            };

            console.log("Payload being sent to save:", payload);

            const response = await fetch("http://localhost:8080/api/whiteboard/save", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(payload),
            });

            if (response.ok) {
                console.log("Whiteboard state saved.");
                alert("Whiteboard state saved successfully!");
            } else {
                console.error("Failed to save state.");
                alert("Failed to save the whiteboard state.");
            }
        } catch (error) {
            console.error("Error saving state:", error);
            alert("Error saving the whiteboard state.");
        }
    };

    /**
     * Load the whiteboard state from the backend.
     */
    const loadState = async () => {
        try {
            const response = await fetch("http://localhost:8080/api/whiteboard/load");
            if (response.ok) {
                const data = await response.json();
                console.log("Loaded state: ", data);
                setStrokes(data.drawingMessages || []); // Update the whiteboard with loaded strokes
                alert("Whiteboard state loaded successfully!");
            } else {
                console.error("Failed to load state.");
                alert("Failed to load the whiteboard state.");
            }
        } catch (error) {
            console.error("Error loading state:", error);
            alert("Error loading the whiteboard state.");
        }
    };

    const handleExportJSON = () => {
        const jsonData = JSON.stringify(strokes, null, 2);
        const blob = new Blob([jsonData], { type: "application/json" });
        const url = URL.createObjectURL(blob);

        const link = document.createElement("a");
        link.href = url;
        link.download = 'whiteboard.json';
        link.click();

        URL.revokeObjectURL(url);
    }

    const handleExportPNG = () => {
        const uri = stageRef.current.toDataURL();
        const link = document.createElement("a");
        link.download = 'whiteboard.png';
        link.href = uri;
        link.click();
    }

    const handleExportPDF = () => {
        if(stageRef && stageRef.current) {
            const uri = stageRef.current.toDataURL();
            const pdf = new jsPDF();
            pdf.addImage(uri, 'PNG', 10, 10, 180, 160);
            pdf.save('whiteboard.pdf');
        }else{
            console.error("stageRef is not properly defined.")
        }
    };

    return (
        <div className="sidebar">
            {/* Export Dropdown */}
            <div className="dropdown">
                <button className="dropdown-button" onClick={toggleDropdown}>
                    Export ▼
                </button>

                {/* Dropdown Menu: Render conditionally based on dropdownVisible.*/}
                {dropdownVisible && (
                    <div className="dropdown-menu">
                        <button onClick={handleExportJSON}>Export as JSON</button>
                        <button onClick={handleExportPDF}>Export as PDF</button>
                        <button onClick={handleExportPNG}>Export as PNG</button>
                    </div>
                )}
            </div>

            {/*Undo and Redo Buttons*/}
            <div className="actions">
                <button onClick={undo}>Undo</button>
                <button onClick={redo}>Redo</button>
            </div>

            {/*Save and Load Buttons*/}
            <div className="save-load">
                <button onClick={saveState}>Save Whiteboard</button>
                <button onClick={loadState}>Load Whiteboard</button>
            </div>
        </div>
    );
};

export default Sidebar;