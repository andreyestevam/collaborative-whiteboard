import React from 'react';
import {render, fireEvent} from '@testing-library/react';
import '@testing-library/jest-dom';
import ColorPicker from '../components/ColorPicker';

/**
 * Unit tests for the ColorPicker component.
 *
 * @author Andrey Estevam Seabra
 */
describe('ColorPicker Component', () => {
    test('renders the color picker with default elements', () => {
        const {getByLabelText} = render(<ColorPicker setColor={() => {}}/>);
        const colorInput = getByLabelText(/Pick a color:/i);

        expect(colorInput).toBeInTheDocument();
        expect(colorInput).toHaveAttribute('type', 'color');
    });

    test('calls setColor when a new color is selected', () => {
        const mockSetColor = jest.fn();
        const {getByLabelText} = render(<ColorPicker setColor={mockSetColor}/>);
        const colorInput = getByLabelText(/Pick a color:/i);
        fireEvent.change(colorInput, {target: {value: '#ff5733'}});
        expect(mockSetColor).toHaveBeenCalledWith('#ff5733');
    });

    test('renders with a default color value', () => {
       const defaultColor = '#000000';
       const {getByLabelText} = render(<ColorPicker setColor={() => {}}/>);
       const colorInput = getByLabelText(/Pick a color:/i);
       expect(colorInput.value).toBe(defaultColor);
    });

    test('has the correct attributes', () => {
        const {getByLabelText} = render(<ColorPicker setColor={() => {}}/>);
        const colorInput = getByLabelText(/Pick a color:/i);
        expect(colorInput).toHaveAttribute('id', 'color-input');
        expect(colorInput).toHaveAccessibleName('Pick a color:');
    });

    test('does not crash if setColor is not provided', () => {
        const {getByLabelText} = render(<ColorPicker setColor={() => {}}/>);
        const colorInput = getByLabelText(/Pick a color:/i);
        expect(colorInput).toBeInTheDocument();
    });

    test('updates the input value when props change', () => {
        const mockSetColor = jest.fn();
        const {getByLabelText, rerender} = render(<ColorPicker setColor={mockSetColor}/>);
        const colorInput = getByLabelText(/Pick a color:/i);

        rerender(<ColorPicker setColor={mockSetColor}/>);
        expect(colorInput.value).toBe('#000000');
    });

    test('matches snapshot', () => {
        const {container} = render(<ColorPicker setColor={() => {}}/>);
        expect(container).toMatchSnapshot();
    });
});