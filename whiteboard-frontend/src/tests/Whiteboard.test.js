import React from "react";
import { render, fireEvent } from "@testing-library/react";
import "@testing-library/jest-dom";
import Whiteboard from "../components/Whiteboard";

jest.mock("konva", () => {
    const Stage = ({ children, onMouseDown, onMouseMove, onMouseUp }) => (
        <div
            data-testid="stage"
            onMouseDown={onMouseDown}
            onMouseMove={onMouseMove}
            onMouseUp={onMouseUp}
        >
            {children}
        </div>
    );
    const Layer = ({ children }) => <div data-testid="layer">{children}</div>;
    const Line = ({ points, stroke, strokeWidth }) => (
        <div
            data-testid="line"
            data-points={points}
            data-stroke={stroke}
            data-stroke-width={strokeWidth}
        ></div>
    );
    return { Stage, Layer, Line };
});

describe("Whiteboard Component", () => {
    let mockSetStrokes, mockSetRedoStack, mockRef;

    beforeEach(() => {
        mockSetStrokes = jest.fn();
        mockSetRedoStack = jest.fn();
        mockRef = {current: null}; // Create a plain mock object for the ref
    });

    test("renders the Whiteboard component", () => {
        const {getByTestId} = render(
            <Whiteboard
                tool="pen"
                color="black"
                lineWidth={2}
                strokes={[]}
                setStrokes={mockSetStrokes}
                redoStack={[]}
                setRedoStack={mockSetRedoStack}
                ref={mockRef}
            />
        );

        expect(getByTestId("stage")).toBeInTheDocument();
        expect(getByTestId("layer")).toBeInTheDocument();
    });

    test("handles mouse down to start a stroke", () => {
        const { getByTestId } = render(
            <Whiteboard
                tool="pen"
                color="black"
                lineWidth={2}
                strokes={[]}
                setStrokes={mockSetStrokes}
                redoStack={[]}
                setRedoStack={mockSetRedoStack}
                ref={mockRef}
            />
        );

        fireEvent.mouseDown(getByTestId("stage"), {
            target: { getStage: () => ({ getPointerPosition: () => ({ x: 10, y: 20 }) }) },
        });

        expect(mockSetStrokes).not.toHaveBeenCalled();
    });

    test("handles mouse move to draw a stroke", () => {
        const { getByTestId } = render(
            <Whiteboard
                tool="pen"
                color="black"
                lineWidth={2}
                strokes={[]}
                setStrokes={mockSetStrokes}
                redoStack={[]}
                setRedoStack={mockSetRedoStack}
                ref={mockRef}
            />
        );

        fireEvent.mouseDown(getByTestId("stage"), {
            target: { getStage: () => ({ getPointerPosition: () => ({ x: 10, y: 20 }) }) },
        });

        fireEvent.mouseMove(getByTestId("stage"), {
            target: { getStage: () => ({ getPointerPosition: () => ({ x: 30, y: 40 }) }) },
        });

        expect(mockSetStrokes).not.toHaveBeenCalled();
    });

    test("handles mouse up to complete a stroke", () => {
        const { getByTestId } = render(
            <Whiteboard
                tool="pen"
                color="black"
                lineWidth={2}
                strokes={[]}
                setStrokes={mockSetStrokes}
                redoStack={[]}
                setRedoStack={mockSetRedoStack}
                ref={mockRef}
            />
        );

        fireEvent.mouseDown(getByTestId("stage"), {
            target: {
                getStage: () => ({
                    getPointerPosition: () => ({ x: 10, y: 20 }),
                }),
            },
        });

        fireEvent.mouseUp(getByTestId("stage"));

        expect(mockSetStrokes).toHaveBeenCalledTimes(1);
        expect(mockSetStrokes).toHaveBeenCalledWith(expect.any(Function));

        const setStrokesCallback = mockSetStrokes.mock.calls[0][0];
        const result = setStrokesCallback([]);
        expect(result).toEqual([{ tool: "pen", color: "black", lineWidth: 2, points: [10, 20] }]);
        expect(mockSetRedoStack).toHaveBeenCalledWith([]);
    });

    test("renders completed strokes", () => {
        const mockStrokes = [
            { points: [10, 20, 30, 40], color: "black", lineWidth: 2 },
        ];

        const { getAllByTestId } = render(
            <Whiteboard
                tool="pen"
                color="black"
                lineWidth={2}
                strokes={mockStrokes}
                setStrokes={mockSetStrokes}
                redoStack={[]}
                setRedoStack={mockSetRedoStack}
                ref={mockRef}
            />
        );

        const lines = getAllByTestId("line");
        expect(lines).toHaveLength(mockStrokes.length);
        expect(lines[0]).toHaveAttribute("data-points", "10,20,30,40");
        expect(lines[0]).toHaveAttribute("data-stroke", "black");
        expect(lines[0]).toHaveAttribute("data-stroke-width", "2");
    });
});