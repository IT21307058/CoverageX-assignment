import { render, screen, waitFor } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import Home from '../../pages/Home';
import ToastContext from '../../context/ToastContext';

jest.mock('../../service/taskApi', () => ({
    fetchTaskData: jest.fn(),
    createTask: jest.fn(),
    markTaskAsCompleted: jest.fn(),
}));

import {
    fetchTaskData,
    createTask,
    markTaskAsCompleted,
} from '../../service/taskApi';

const renderWithToast = () => {
    const toastMock = { success: jest.fn(), error: jest.fn() };
    render(
        <ToastContext.Provider value={{ toast: toastMock }}>
            <Home />
        </ToastContext.Provider>
    );
    return { toastMock };
};

beforeEach(() => jest.resetAllMocks());

describe('Home page', () => {
    test('shows spinner, then renders tasks after initial fetch (fetch only once on mount)', async () => {
        fetchTaskData.mockResolvedValueOnce([
            { id: 1, title: 'Task A', description: 'Desc A', status: 'NOT_DONE' },
        ]);

        renderWithToast();

        expect(screen.getByRole('status')).toBeInTheDocument();

        await waitFor(() =>
            expect(screen.queryByRole('status')).not.toBeInTheDocument()
        );
        expect(screen.getByText('Task A')).toBeInTheDocument();

        expect(fetchTaskData).toHaveBeenCalledTimes(1);
    });

    test('adding a task calls createTask and triggers a refetch (task then appears)', async () => {
        fetchTaskData.mockResolvedValueOnce([]);

        renderWithToast();

        await waitFor(() =>
            expect(screen.queryByRole('status')).not.toBeInTheDocument()
        );

        createTask.mockResolvedValueOnce({
            id: 101,
            title: 'New Task',
            description: 'New Desc',
            status: 'NOT_DONE',
        });
        fetchTaskData.mockResolvedValueOnce([
            { id: 101, title: 'New Task', description: 'New Desc', status: 'NOT_DONE' },
        ]);

        await userEvent.type(screen.getByLabelText(/title/i), 'New Task');
        await userEvent.type(screen.getByLabelText(/description/i), 'New Desc');
        await userEvent.click(screen.getByRole('button', { name: /add/i }));

        expect(createTask).toHaveBeenCalledWith({ title: 'New Task', description: 'New Desc' });

        await waitFor(() => expect(fetchTaskData).toHaveBeenCalledTimes(2));
        expect(screen.getByText('New Task')).toBeInTheDocument();
        expect(screen.getByText('New Desc')).toBeInTheDocument();
    });

    test('marking a task as completed calls API and triggers a refetch (task disappears)', async () => {
        fetchTaskData.mockResolvedValueOnce([
            { id: 5, title: 'Do me', description: 'Please', status: 'NOT_DONE' },
        ]);

        renderWithToast();

        await waitFor(() =>
            expect(screen.queryByRole('status')).not.toBeInTheDocument()
        );
        expect(screen.getByText('Do me')).toBeInTheDocument();

        markTaskAsCompleted.mockResolvedValueOnce({
            id: 5, title: 'Do me', description: 'Please', status: 'DONE',
        });
        fetchTaskData.mockResolvedValueOnce([]);

        await userEvent.click(screen.getByRole('button', { name: /done/i }));

        expect(markTaskAsCompleted).toHaveBeenCalledWith(5);
        await waitFor(() => expect(fetchTaskData).toHaveBeenCalledTimes(2));

        expect(screen.queryByText('Do me')).not.toBeInTheDocument();
    });

    test('shows error alert when initial fetch fails and calls toast.error', async () => {
        const error = new Error('boom');
        fetchTaskData.mockRejectedValueOnce(error);

        const { toastMock } = renderWithToast();

        await waitFor(() =>
            expect(screen.queryByRole('status')).not.toBeInTheDocument()
        );

        expect(screen.getByText(/failed to fetch tasks/i)).toBeInTheDocument();
        expect(toastMock.error).toHaveBeenCalledWith('Failed to fetch tasks');
    });
});