export const fetchTaskData = async (date) => {
  const apiUrl = `http://localhost:8080/tasks?page=0&size=5`;

  try {
    const response = await fetch(apiUrl);
    if (!response.ok) {
      throw new Error('Failed to fetch data');
    }
    const data = await response.json();
    return data.content;
  } catch (error) {
    console.error('Error fetching data:', error);
    throw error;
  }
};

export const createTask = async (formData) => {
  console.log("Creating task with data:", formData);
  const response = await fetch('http://localhost:8080/tasks', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json', 
    },
    body: JSON.stringify(formData),
  });
  if (!response.ok) throw new Error('Failed to create post');
  return response.json();
};


export const markTaskAsCompleted = async (taskId) => {
  const response = await fetch(`http://localhost:8080/tasks/${taskId}/markCompleted`, {
    method: 'PATCH',
  });
  if (!response.ok) {
    throw new Error('Failed to mark task as completed');
  }
  return response.json();
};