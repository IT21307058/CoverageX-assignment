import React from 'react';

const TaskItem = ({ task, onMarkCompleted  }) => {
    const handleMarkCompleted = () => {
        onMarkCompleted(task.id);  
    };

    return (
        <div className="d-flex justify-content-between align-items-center mb-3 p-3 border rounded shadow-sm">
            <div>
                <h5>{task.title}</h5>
                <p>{task.description}</p>
            </div>
            <button className="btn btn-success" onClick={handleMarkCompleted}>
                Done
            </button>
        </div>
    );
};

export default TaskItem;
