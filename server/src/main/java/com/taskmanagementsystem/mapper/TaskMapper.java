package com.taskmanagementsystem.mapper;

import org.springframework.stereotype.Component;

import com.taskmanagementsystem.dto.TaskDTO;
import com.taskmanagementsystem.model.Task;
import com.taskmanagementsystem.model.TaskStatus;

@Component
public class TaskMapper {
    public Task toEntity(TaskDTO dto) {
        Task t = new Task();
        t.setTitle(dto.getTitle());
        t.setDescription(dto.getDescription());
        t.setStatus(
                dto.getStatus() != null ? dto.getStatus() : TaskStatus.NOT_DONE);
        return t;
    }

    public TaskDTO mapToDTO(Task t) {
        TaskDTO dto = new TaskDTO();
        dto.setId(t.getId());
        dto.setTitle(t.getTitle());
        dto.setDescription(t.getDescription());
        dto.setStatus(t.getStatus());
        return dto;
    }
}
