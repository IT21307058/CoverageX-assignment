import React, { useState, useContext } from 'react';
import ToastContext from '../../context/ToastContext';

const TaskForm = ({ onAddTask }) => {
    const { toast } = useContext(ToastContext);

    const [title, setTitle] = useState('');
    const [description, setDescription] = useState('');

    const handleAdd = () => {
        if (!title || !description) {
            toast.error("Please fill in both the title and description.");
            return;
        }
        onAddTask({ title, description });
        setTitle('');
        setDescription('');
    };

    return (
        <div className="container py-4">
            <h3 className="mb-3"> <b>Add a Task</b></h3>
            <div className="mb-3">
                <label htmlFor="title" className="form-label">Title</label>
                <input
                    type="text"
                    className="form-control"
                    id="title"
                    value={title}
                    onChange={(e) => setTitle(e.target.value)}
                    data-testid="title-input"
                    placeholder="Enter task title"
                />
            </div>
            <div className="mb-3">
                <label htmlFor="description" className="form-label">Description</label>
                <textarea
                    className="form-control"
                    id="description"
                    value={description}
                    onChange={(e) => setDescription(e.target.value)}
                    data-testid="description-input"
                    placeholder="Enter task description"
                />
            </div>
            <div className="text-end">
                <button className="btn btn-primary px-4" data-testid="add-btn" onClick={handleAdd}>
                    Add
                </button>
            </div>
        </div>
    );
};

export default TaskForm;
