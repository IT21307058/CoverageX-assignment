import React from 'react';
import TaskItem from './TaskItem';

const TaskList = ({ tasks, onMarkCompleted  }) => {
    return (
        <div className="container py-4">
            {tasks.length === 0 ? (
                <p>No tasks available</p>
            ) : (
                tasks.filter(task => task.status !== 'DONE').map((task) => (
                    <TaskItem key={task.id} task={task} onMarkCompleted={onMarkCompleted} />
                ))
            )}
        </div>
    );
};

export default TaskList;
