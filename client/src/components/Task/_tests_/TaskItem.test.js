import { render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import TaskList from '../TaskList';

// A minimal fake TaskItem mock to isolate TaskList behavior
jest.mock('../TaskItem', () => ({ task, onMarkCompleted }) => (
    <div data-testid={`task-${task.id}`}>
        <span>{task.title}</span>
        <button onClick={() => onMarkCompleted(task.id)}>Done</button>
    </div>
));

describe('TaskList component', () => {
    test('renders "No tasks available" when empty', () => {
        render(<TaskList tasks={[]} onMarkCompleted={jest.fn()} />);
        expect(screen.getByText(/no tasks available/i)).toBeInTheDocument();
    });

    test('renders only NOT_DONE tasks', () => {
        const tasks = [
            { id: 1, title: 'Active', description: 'desc', status: 'NOT_DONE' },
            { id: 2, title: 'Completed', description: 'desc', status: 'DONE' },
        ];
        render(<TaskList tasks={tasks} onMarkCompleted={jest.fn()} />);
        expect(screen.getByText('Active')).toBeInTheDocument();
        expect(screen.queryByText('Completed')).not.toBeInTheDocument();
    });

    test('calls onMarkCompleted when "Done" button clicked', async () => {
        const user = userEvent.setup();
        const onMarkCompleted = jest.fn();
        const tasks = [{ id: 5, title: 'Click Me', description: 'desc', status: 'NOT_DONE' }];

        render(<TaskList tasks={tasks} onMarkCompleted={onMarkCompleted} />);

        await user.click(screen.getByText(/done/i));
        expect(onMarkCompleted).toHaveBeenCalledTimes(1);
        expect(onMarkCompleted).toHaveBeenCalledWith(5);
    });
});