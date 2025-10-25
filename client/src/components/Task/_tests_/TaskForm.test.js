import { render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import TaskForm from '../../Task/TaskForm';
import ToastContext from '../../../context/ToastContext';

const renderWithToast = (ui, toastMock = { success: jest.fn(), error: jest.fn() }) =>
({
    toastMock,
    ...render(<ToastContext.Provider value={{ toast: toastMock }}>{ui}</ToastContext.Provider>)
});

describe('TaskForm', () => {
    test('shows validation toast when fields are empty', async () => {
        const user = userEvent.setup();
        const onAddTask = jest.fn();
        const { toastMock } = renderWithToast(<TaskForm onAddTask={onAddTask} />);

        await user.click(screen.getByRole('button', { name: /add/i }));

        expect(onAddTask).not.toHaveBeenCalled();
        expect(toastMock.error).toHaveBeenCalledWith(
            'Please fill in both the title and description.'
        );
    });

    test('shows validation when only one field is filled (title only)', async () => {
        const user = userEvent.setup();
        const onAddTask = jest.fn();
        const { toastMock } = renderWithToast(<TaskForm onAddTask={onAddTask} />);

        await user.type(screen.getByLabelText(/title/i), 'New Task');
        await user.click(screen.getByRole('button', { name: /add/i }));

        expect(onAddTask).not.toHaveBeenCalled();
        expect(toastMock.error).toHaveBeenCalled();
    });

    test('calls onAddTask with title & description and clears inputs', async () => {
        const user = userEvent.setup();
        const onAddTask = jest.fn();
        renderWithToast(<TaskForm onAddTask={onAddTask} />);

        // Fill fields
        await user.type(screen.getByLabelText(/title/i), 'New Task');
        await user.type(screen.getByLabelText(/description/i), 'Desc');

        // Click Add
        await user.click(screen.getByRole('button', { name: /add/i }));

        // Called with correct payload
        expect(onAddTask).toHaveBeenCalledTimes(1);
        expect(onAddTask).toHaveBeenCalledWith({ title: 'New Task', description: 'Desc' });

        // Inputs cleared
        expect(screen.getByLabelText(/title/i)).toHaveValue('');
        expect(screen.getByLabelText(/description/i)).toHaveValue('');
    });
});