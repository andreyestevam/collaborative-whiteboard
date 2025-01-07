import React from 'react';
import {render, screen, fireEvent} from '@testing-library/react';
import '@testing-library/jest-dom';
import Sidebar from '../components/Sidebar';

/**
 * Unit tests for the Sidebar component.
 *
 * @author Andrey Estevam Seabra
 */
describe('Sidebar Component', () => {
    let mockUndo, mockRedo, mockStageRef, mockStrokes;

    beforeEach(() => {
        mockUndo = jest.fn();
        mockRedo = jest.fn();
        mockStrokes = [{tool: 'pen', color: 'black', points: [10,20,30,40], lineWidth: 2}];
        mockStageRef = {current: {toDataURL: jest.fn(() => 'mock-data-url')}};

    });

    test('renders the export dropdown and undo/redo buttons', () => {
        render(
            <Sidebar
                undo={mockUndo}
                redo={mockRedo}
                strokes={mockStrokes}
                stageRef={mockStageRef}
            />
        );

        expect(screen.getByText(/Export ▼/i)).toBeInTheDocument();
        expect(screen.getByText(/Undo/i)).toBeInTheDocument();
        expect(screen.getByText(/Redo/i)).toBeInTheDocument();
    });

    test('toggles the export dropdown visibility', () => {
        render(
            <Sidebar
                undo={mockUndo}
                redo={mockRedo}
                strokes={mockStrokes}
                stageRef={mockStageRef}
            />
        );

        expect(screen.queryByText(/Export as JSON/i)).not.toBeInTheDocument();

        fireEvent.click(screen.getByText(/Export ▼/i));
        expect(screen.getByText(/Export as JSON/i)).toBeInTheDocument();

        fireEvent.click(screen.getByText(/Export ▼/i));
        expect(screen.queryByText(/Export as JSON/i)).not.toBeInTheDocument();
    });

    test('calls undo when Undo button is clicked', () => {
        render(
            <Sidebar
                undo={mockUndo}
                redo={mockRedo}
                strokes={mockStrokes}
                stageRef={mockStageRef}
            />
        );

        fireEvent.click(screen.getByText(/Undo/i));
        expect(mockUndo).toHaveBeenCalledTimes(1);
    });

    test('calls redo when Redo button is clicked', () => {
        render(
            <Sidebar
                undo={mockUndo}
                redo={mockRedo}
                strokes={mockStrokes}
                stageRef={mockStageRef}
            />
        );

        fireEvent.click(screen.getByText(/Redo/i));
        expect(mockRedo).toHaveBeenCalledTimes(1);
    });

    test('exports strokes as PNG', () => {
        render(
            <Sidebar
                undo={mockUndo}
                redo={mockRedo}
                strokes={mockStrokes}
                stageRef={mockStageRef}
            />
        );

        fireEvent.click(screen.getByText(/Export ▼/i));
        fireEvent.click(screen.getByText(/Export as PNG/i));
        expect(mockStageRef.current.toDataURL).toHaveBeenCalled();
    });
});