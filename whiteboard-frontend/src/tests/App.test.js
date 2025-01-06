import { render, screen } from '@testing-library/react';
import App from '../App';

test('renders Toolbar buttons', () => {
  render(<App />);
  const penButton = screen.getByText(/Pen/i); // Match "Pen" button
  expect(penButton).toBeInTheDocument();
});