package com.taskmanagementsystem.dto;

import com.taskmanagementsystem.model.TaskStatus;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TaskDTO {
    private Long id; 

    @NotBlank(message = "Title is mandatory")
    private String title;

    @NotBlank(message = "Description is mandatory")
    private String description;

    private TaskStatus status = TaskStatus.NOT_DONE;
}
