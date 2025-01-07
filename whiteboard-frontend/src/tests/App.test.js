import { render, screen } from '@testing-library/react';
import App from '../App';

/**
 * Unit tests for the App component.
 *
 * @author Andrey Estevam Seabra
 */
test('renders Toolbar buttons', () => {
  render(<App />);
  const penButton = screen.getByText(/Pen/i); // Match "Pen" button
  expect(penButton).toBeInTheDocument();
});