package com.taskmanagementsystem.repository;

import com.taskmanagementsystem.model.Task;
import com.taskmanagementsystem.model.TaskStatus;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Page<Task> findByStatusNot(TaskStatus status, Pageable pageable);

}
