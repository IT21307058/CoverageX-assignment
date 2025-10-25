import React, { useEffect, useState, useContext } from "react";
import "../App.css";
import TaskForm from '../components/Task/TaskForm';
import TaskList from '../components/Task/TaskList';
import { fetchTaskData, createTask, markTaskAsCompleted } from '../service/taskApi';
import ToastContext from "../context/ToastContext";

const Home = () => {
  const { toast } = useContext(ToastContext);

  const [tasks, setTasks] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [refresh, setRefresh] = useState(false);

  const addTask = async (newTask) => {
    try {
      console.log("Adding task:", newTask);
      const addedTask = await createTask(newTask);
      // setTasks([addedTask, ...tasks]);
      toast.success("Task added successfully!");
      setRefresh((prev) => !prev);
    } catch (error) {
      console.error("Error adding task:", error);
      setError("Failed to add task");
    }
  };

  const handleMarkCompleted = async (taskId) => {
    try {
      const updatedTask = await markTaskAsCompleted(taskId);
      // setTasks(tasks.map(task => task.id === updatedTask.id ? updatedTask : task));
      toast.success("Task marked as completed!");
      setRefresh((prev) => !prev);
    } catch (error) {
      console.error("Error marking task as completed:", error);
      setError("Failed to mark task as completed");
    }
  };

  useEffect(() => {
    const delayLoading = setTimeout(async () => {
      try {
        const data = await fetchTaskData();
        setTasks(data);
      } catch (e) {
        setError("Failed to fetch tasks");
        toast.error("Failed to fetch tasks");
      } finally {
        setLoading(false);
      }
    }, 500); 

    return () => clearTimeout(delayLoading);
  }, [refresh]);

  if (loading) return (
    <div className="d-flex justify-content-center py-4">
      <div className="spinner-border text-primary" role="status">
        <span className="visually-hidden">Loading...</span>
      </div>
    </div>
  );
  if (error) return <div className="alert alert-danger m-3">{error}</div>;

  return (
    <>
      <div className="container py-4">
        <div className="row align-items-start">
          <div className="col-12 col-md-6">
            <TaskForm onAddTask={addTask} />
          </div>
          <div className="col-12 col-md-6">
            <TaskList tasks={tasks} onMarkCompleted={handleMarkCompleted} />
          </div>
        </div>
      </div>
    </>
  );
};

export default Home;


