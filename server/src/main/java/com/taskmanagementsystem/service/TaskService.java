package com.taskmanagementsystem.service;

import com.taskmanagementsystem.dto.TaskDTO;
import com.taskmanagementsystem.exception.TaskNotFoundException;
import com.taskmanagementsystem.mapper.TaskMapper;
import com.taskmanagementsystem.model.Task;
import com.taskmanagementsystem.model.TaskStatus;
import com.taskmanagementsystem.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class TaskService implements TaskServiceInterface {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskMapper taskMapper;

    // create task
    public TaskDTO createTask(TaskDTO taskDTO) {
        Task entity = taskMapper.toEntity(taskDTO);
        Task saved = taskRepository.save(entity);
        return taskMapper.mapToDTO(saved);
    }

    public Page<TaskDTO> getAllTasks(Pageable pageable) {
        Pageable sortedByDate = PageRequest.of(pageable.getPageNumber(), 5, Sort.by(Sort.Order.desc("createdDate")));

        return taskRepository.findByStatusNot(TaskStatus.DONE, sortedByDate)
                .map(task -> taskMapper.mapToDTO(task));
    }

    // Get one
    public TaskDTO getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        return taskMapper.mapToDTO(task);
    }

    // Update
    public TaskDTO updateTask(Long id, TaskDTO dto) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setStatus(dto.getStatus());

        Task updated = taskRepository.save(task);
        return taskMapper.mapToDTO(updated);
    }

    // Delete
    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        taskRepository.delete(task);
    }

    public TaskDTO markTaskAsCompleted(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        task.setStatus(TaskStatus.DONE); 
        taskRepository.save(task);
        return taskMapper.mapToDTO(task); 
    }

}
