import React from 'react';
import '../styles/ColorPicker.css';

/**
 * Provides a user interface for selecting a color.
 *
 * @param setColor (function) a callback to update the selected color in the parent component.
 *
 * @returns {Element} a React element rendering a color picker.
 *
 * @author Andrey Estevam Seabra
 */
const ColorPicker = ({setColor}) => {
    const handleColorChange = (e) => {
        setColor(e.target.value);
    }

    return (
        <div className="color-picker">
            <label htmlFor="color-input">Pick a color:</label>
            <input
                id="color-input"
                type="color"
                onChange={handleColorChange} // Trigger color change when the input changes.
            />
        </div>
    );
};

export default ColorPicker;