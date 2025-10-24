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
            <h2>Add a Task</h2>
            <div className="mb-3">
                <label htmlFor="title" className="form-label">Title</label>
                <input
                    type="text"
                    className="form-control"
                    id="title"
                    value={title}
                    onChange={(e) => setTitle(e.target.value)}
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
                    placeholder="Enter task description"
                />
            </div>
            <button className="btn btn-primary" onClick={handleAdd}>Add</button>
        </div>
    );
};

export default TaskForm;
