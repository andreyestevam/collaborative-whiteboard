import React from "react";
import { render, fireEvent } from "@testing-library/react";
import "@testing-library/jest-dom";
import Toolbar from "../components/Toolbar";

/**
 * Unit tests for the Toolbar component.
 *
 * @author Andrey Estevam Seabra
 */
describe("Toolbar Component", () => {
    let mockSetTool, mockSetColor, mockSetLineWidth, mockClearWhiteboard;

    beforeEach(() => {
        // Mock functions
        mockSetTool = jest.fn();
        mockSetColor = jest.fn();
        mockSetLineWidth = jest.fn();
        mockClearWhiteboard = jest.fn();
    });

    test("renders all toolbar elements", () => {
        const { getByText, getByLabelText } = render(
            <Toolbar
                setTool={mockSetTool}
                setColor={mockSetColor}
                setLineWidth={mockSetLineWidth}
                clearWhiteboard={mockClearWhiteboard}
            />
        );

        expect(getByText(/Pen/i)).toBeInTheDocument();
        expect(getByText(/Eraser/i)).toBeInTheDocument();
        expect(getByLabelText(/Select stroke color/i)).toBeInTheDocument();
        expect(getByLabelText(/Adjust line width/i)).toBeInTheDocument();
        expect(getByText(/Clear/i)).toBeInTheDocument();
    });

    test("calls setTool with 'pen' when the Pen button is clicked", () => {
        const { getByText } = render(
            <Toolbar
                setTool={mockSetTool}
                setColor={mockSetColor}
                setLineWidth={mockSetLineWidth}
                clearWhiteboard={mockClearWhiteboard}
            />
        );

        fireEvent.click(getByText(/Pen/i));
        expect(mockSetTool).toHaveBeenCalledWith("pen");
    });

    test("calls setTool with 'eraser' when the Eraser button is clicked", () => {
        const { getByText } = render(
            <Toolbar
                setTool={mockSetTool}
                setColor={mockSetColor}
                setLineWidth={mockSetLineWidth}
                clearWhiteboard={mockClearWhiteboard}
            />
        );

        fireEvent.click(getByText(/Eraser/i));
        expect(mockSetTool).toHaveBeenCalledWith("eraser");
    });

    test("calls setColor when a new color is selected", () => {
        const { getByLabelText } = render(
            <Toolbar
                setTool={mockSetTool}
                setColor={mockSetColor}
                setLineWidth={mockSetLineWidth}
                clearWhiteboard={mockClearWhiteboard}
            />
        );

        fireEvent.change(getByLabelText(/Select stroke color/i), { target: { value: "#ff5733" } });
        expect(mockSetColor).toHaveBeenCalledWith("#ff5733");
    });

    test("calls setLineWidth when the line width is adjusted", () => {
        const { getByLabelText } = render(
            <Toolbar
                setTool={mockSetTool}
                setColor={mockSetColor}
                setLineWidth={mockSetLineWidth}
                clearWhiteboard={mockClearWhiteboard}
            />
        );

        fireEvent.change(getByLabelText(/Adjust line width/i), { target: { value: "5" } });
        expect(mockSetLineWidth).toHaveBeenCalledWith("5");
    });

    test("calls clearWhiteboard when the Clear button is clicked", () => {
        const { getByText } = render(
            <Toolbar
                setTool={mockSetTool}
                setColor={mockSetColor}
                setLineWidth={mockSetLineWidth}
                clearWhiteboard={mockClearWhiteboard}
            />
        );

        fireEvent.click(getByText(/Clear/i));
        expect(mockClearWhiteboard).toHaveBeenCalledTimes(1);
    });
});