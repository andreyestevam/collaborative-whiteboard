import React from "react";

/**
 * Toolbar component. Provides controls for drawing, including tool selection,
 * color selection, line width adjustment, and clearing the whiteboard.
 *
 * @param setTool (function) sets the current drawing tool ("pen", "eraser").
 * @param setColor (function) sets the current stroke color.
 * @param setLineWidth (function) sets the stroke width.
 * @param clearWhiteboard (function) clears the entire whiteboard.
 *
 * @returns {Element} a React element rendering the toolbar UI.
 *
 * @author Andrey Estevam Seabra
 */
const Toolbar = ({setTool, setColor, setLineWidth, clearWhiteboard}) => {
    return (
        <div className="toolbar">
            {/*Buttons to select the drawing and eraser tool.*/}
            <button onClick={() => setTool('pen')}>Pen</button>
            <button onClick={() => setTool('eraser')}>Eraser</button>

            {/*Color picker to set the drawing color.*/}
            <input
                type="color"
                onChange={(e) => setColor(e.target.value)}
                aria-label="Select stroke color"
            />

            {/*Range input slider to adjust the line width.*/}
            <input
                type="range"
                min="1"
                max="10"
                defaultValue="2"
                onChange={(e) => setLineWidth(e.target.value)}
                aria-label="Adjust line width"
            />

            {/*Button to clear the whiteboard.*/}
            <button onClick={clearWhiteboard}>Clear</button>
        </div>
    )
}

export default Toolbar;