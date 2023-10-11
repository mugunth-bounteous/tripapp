import React from 'react';
import { render, fireEvent, waitFor ,screen} from '@testing-library/react';
import Register from './Register';
import axios from 'axios';
import { BrowserRouter as Router } from 'react-router-dom';
import { useSnackbar } from 'notistack';

// jest.mock('notistack', () => ({
//   useSnackbar: jest.fn().mockReturnValue({
//     enqueueSnackbar: jest.fn(),
//   }),
// }));

jest.mock('axios');

describe('Register', () => {
  it('renders all details', () => {
    render(<Router><Register /></Router>);
    expect(screen.getByPlaceholderText('Username')).toBeInTheDocument();
    expect(screen.getByPlaceholderText('First Name')).toBeInTheDocument();
    expect(screen.getByPlaceholderText('Last Name')).toBeInTheDocument();
    expect(screen.getByPlaceholderText('Password')).toBeInTheDocument();
    expect(screen.getByPlaceholderText('Confirm Password')).toBeInTheDocument();
  });

  it('handles input changes', () => {
    render(<Router><Register /></Router>);
    fireEvent.change(screen.getByPlaceholderText('Username'), { target: { value: 'testuser' } });
    fireEvent.change(screen.getByPlaceholderText('First Name'), { target: { value: 'testfirst' } });
    fireEvent.change(screen.getByPlaceholderText('Last Name'), { target: { value: 'testlast' } });
    fireEvent.change(screen.getByPlaceholderText('Password'), { target: { value: 'testpass' } });
    fireEvent.change(screen.getByPlaceholderText('Confirm Password'), { target: { value: 'testpass' } });
    expect(screen.getByPlaceholderText('Username').value).toBe('testuser');
    expect(screen.getByPlaceholderText('First Name').value).toBe('testfirst');
    expect(screen.getByPlaceholderText('Last Name').value).toBe('testlast');
    expect(screen.getByPlaceholderText('Password').value).toBe('testpass');
    expect(screen.getByPlaceholderText('Confirm Password').value).toBe('testpass');
  });

  it('handles form submission', async () => {
    axios.post.mockResolvedValue({ data: { message: 'Registration successful' } });
    render(<Router><Register /></Router>);
    fireEvent.change(screen.getByPlaceholderText('Username'), { target: { value: 'testuser' } });
    fireEvent.change(screen.getByPlaceholderText('First Name'), { target: { value: 'testfirst' } });
    fireEvent.change(screen.getByPlaceholderText('Last Name'), { target: { value: 'testlast' } });
    fireEvent.change(screen.getByPlaceholderText('Password'), { target: { value: 'testpass' } });
    fireEvent.change(screen.getByPlaceholderText('Confirm Password'), { target: { value: 'testpass' } });
    fireEvent.click(screen.getByRole('button'));
    await waitFor(() => expect(axios.post).toHaveBeenCalledTimes(1));
  });
  
//   it('handles non matching password', async () => {
//     render(<Router><Register /></Router>);
//     fireEvent.change(screen.getByPlaceholderText('Username'), { target: { value: 'testuser' } });
//     fireEvent.change(screen.getByPlaceholderText('First Name'), { target: { value: 'testfirst' } });
//     fireEvent.change(screen.getByPlaceholderText('Last Name'), { target: { value: 'testlast' } });
//     fireEvent.change(screen.getByPlaceholderText('Password'), { target: { value: 'testpa' } });
//     fireEvent.change(screen.getByPlaceholderText('Confirm Password'), { target: { value: 'testpass' } });
//     fireEvent.click(screen.getByRole('button'));
//     const enqueueSnackbar = jest.fn();
//     useSnackbar.mockReturnValue({ enqueueSnackbar });
//     await waitFor(() => expect(enqueueSnackbar).toHaveBeenCalledWith('Passwords are not matching!', { variant: 'error' }));
//     // await waitFor(() => expect(axios.post).toHaveBeenCalledTimes(0));
//   });

});
