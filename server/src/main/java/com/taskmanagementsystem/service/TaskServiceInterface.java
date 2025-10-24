package com.taskmanagementsystem.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.taskmanagementsystem.dto.TaskDTO;

public interface TaskServiceInterface {
    TaskDTO createTask(TaskDTO dto);

    Page<TaskDTO> getAllTasks(Pageable p);

    TaskDTO getTaskById(Long id);

    TaskDTO updateTask(Long id, TaskDTO dto);

    void deleteTask(Long id);
}
