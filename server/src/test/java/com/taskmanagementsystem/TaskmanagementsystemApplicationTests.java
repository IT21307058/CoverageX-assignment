package com.taskmanagementsystem;

import com.taskmanagementsystem.dto.TaskDTO;
import com.taskmanagementsystem.mapper.TaskMapper;
import com.taskmanagementsystem.model.Task;
import com.taskmanagementsystem.model.TaskStatus;
import com.taskmanagementsystem.repository.TaskRepository;
import com.taskmanagementsystem.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class TaskmanagementsystemApplicationTests {

	@Mock
	private TaskRepository taskRepository;

	@Mock
	private TaskMapper taskMapper;

	@InjectMocks
	private TaskService taskService;

	private Task task;
	private TaskDTO dto;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);

		task = new Task();
		task.setId(1L);
		task.setTitle("Test Task");
		task.setDescription("Test Description");
		task.setStatus(TaskStatus.NOT_DONE);

		dto = new TaskDTO();
		dto.setId(1L);
		dto.setTitle("Test Task");
		dto.setDescription("Test Description");
		dto.setStatus(TaskStatus.NOT_DONE);
	}

	@Test
	void createTask_ShouldReturnSavedDTO() {
		// Mock behavior of taskMapper
		when(taskMapper.toEntity(any(TaskDTO.class))).thenReturn(task);
		when(taskRepository.save(any(Task.class))).thenReturn(task);
		when(taskMapper.mapToDTO(any(Task.class))).thenReturn(dto);

		TaskDTO result = taskService.createTask(dto);

		assertNotNull(result);
		assertEquals(dto.getTitle(), result.getTitle());
		assertEquals(dto.getDescription(), result.getDescription());
		assertEquals(TaskStatus.NOT_DONE, result.getStatus());
		verify(taskRepository, times(1)).save(any(Task.class));
		verify(taskMapper, times(1)).toEntity(any(TaskDTO.class));
		verify(taskMapper, times(1)).mapToDTO(any(Task.class));
	}

	@Test
	void getTaskById_ShouldReturnTaskDTO_WhenFound() {
		// Mock behavior of taskRepository and taskMapper
		when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
		when(taskMapper.mapToDTO(any(Task.class))).thenReturn(dto);

		TaskDTO result = taskService.getTaskById(1L);

		assertNotNull(result);
		assertEquals(task.getId(), result.getId());
		assertEquals(task.getTitle(), result.getTitle());
		verify(taskRepository, times(1)).findById(1L);
		verify(taskMapper, times(1)).mapToDTO(any(Task.class));
	}

	@Test
	void updateTask_ShouldReturnUpdatedTaskDTO() {
		// Mock behavior of taskRepository and taskMapper
		when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
		when(taskRepository.save(any(Task.class))).thenReturn(task);
		when(taskMapper.mapToDTO(any(Task.class))).thenReturn(dto);

		dto.setDescription("Updated Description");
		dto.setStatus(TaskStatus.DONE);

		TaskDTO result = taskService.updateTask(1L, dto);

		assertNotNull(result);
		assertEquals("Updated Description", result.getDescription());
		assertEquals(TaskStatus.DONE, result.getStatus());
		verify(taskRepository, times(1)).save(any(Task.class));
		verify(taskMapper, times(1)).mapToDTO(any(Task.class));
	}

	@Test
	void deleteTask_ShouldCallRepositoryDelete() {
		// Mock behavior of taskRepository
		when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

		taskService.deleteTask(1L);

		verify(taskRepository, times(1)).findById(1L);
		verify(taskRepository, times(1)).delete(task);
	}
}
