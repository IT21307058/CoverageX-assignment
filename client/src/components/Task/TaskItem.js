import React from 'react';

const TaskItem = ({ task, onMarkCompleted }) => {
    const handleMarkCompleted = () => {
        onMarkCompleted(task.id);
    };

    return (
        <div className="task-card d-flex justify-content-between align-items-center mb-3 p-3" data-testid={`task-${task.id}`}>
            <div>
                <div className="task-title">{task.title}</div>
                <p className="task-desc">{task.description}</p>
            </div>
            <button className="btn-done" data-testid={`done-btn-${task.id}`} onClick={handleMarkCompleted}>
                Done
            </button>
        </div>
    );
};

export default TaskItem;
