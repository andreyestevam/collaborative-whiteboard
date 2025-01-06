import React, {useState} from "react";
import {jsPDF} from 'jspdf';

const Sidebar = ({undo, redo, strokes, setStrokes}) => {
    // State to toggle dropdown visibility
    const [dropdownVisible, setDropdownVisible] = useState(false);

    // Toggle dropdown visibility
    const toggleDropdown = () => {
        setDropdownVisible(!dropdownVisible);
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
        const uri = stageRef.current.toDataURL();
        const pdf = new jsPDF();
        pdf.addImage(uri, 'PNG', 10, 10, 180, 160);
        pdf.save('whiteboard.pdf');
    }

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
        </div>
    )
}

export default Sidebar;