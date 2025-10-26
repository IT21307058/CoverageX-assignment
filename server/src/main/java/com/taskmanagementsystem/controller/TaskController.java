package com.taskmanagementsystem.controller;

import com.taskmanagementsystem.dto.TaskDTO;
import com.taskmanagementsystem.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    // create task
    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@Valid @RequestBody TaskDTO taskDTO) {
        TaskDTO created = taskService.createTask(taskDTO);
        return ResponseEntity.status(201).body(created);
    }

    @GetMapping
    public Page<TaskDTO> getAllTasks(@RequestParam(defaultValue = "5") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return taskService.getAllTasks(pageable);
    }

    @PatchMapping("/{id}/markCompleted")
    public ResponseEntity<TaskDTO> markTaskAsCompleted(@PathVariable Long id) {
        TaskDTO taskDTO = taskService.markTaskAsCompleted(id); 
        return ResponseEntity.ok(taskDTO);
    }

}