import React from 'react';

const TaskItem = ({ task, onMarkCompleted }) => {
    const handleMarkCompleted = () => {
        onMarkCompleted(task.id);
    };

    return (
        <div className="task-card d-flex justify-content-between align-items-center mb-3 p-3">
            <div>
                <div className="task-title">{task.title}</div>
                <p className="task-desc">{task.description}</p>
            </div>
            <button className="btn-done" onClick={handleMarkCompleted}>
                Done
            </button>
        </div>
    );
};

export default TaskItem;
