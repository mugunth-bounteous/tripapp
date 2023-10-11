import React from 'react';
import { render, fireEvent, waitFor } from '@testing-library/react';
import Login from './Login';
import axios from 'axios';
import { BrowserRouter as Router } from 'react-router-dom';
import { screen } from '@testing-library/react';

jest.mock('axios');

describe('Login', () => {
  it('renders correctly', () => {
    const { getByPlaceholderText } = render(<Router><Login /></Router>);
    expect(screen.getByPlaceholderText('Username')).toBeInTheDocument();
    expect(screen.getByPlaceholderText('Password')).toBeInTheDocument();
  });

  it('handles input changes', () => {
    const { getByPlaceholderText } = render(<Router><Login /></Router>);
    fireEvent.change(screen.getByPlaceholderText('Username'), { target: { value: 'testuser' } });
    fireEvent.change(screen.getByPlaceholderText('Password'), { target: { value: 'testpass' } });
    expect(screen.getByPlaceholderText('Username').value).toBe('testuser');
    expect(screen.getByPlaceholderText('Password').value).toBe('testpass');
  });

  it('handles form submission', async () => {
    axios.post.mockResolvedValue({ data: { user: 'testuser' } });
    const { getByPlaceholderText, getByText } = render(<Router><Login /></Router>);
    fireEvent.change(screen.getByPlaceholderText('Username'), { target: { value: 'testuser' } });
    fireEvent.change(screen.getByPlaceholderText('Password'), { target: { value: 'testpass' } });
    fireEvent.click(screen.getByRole("button"));
    await waitFor(() => expect(axios.post).toHaveBeenCalledTimes(1));
  });
});
