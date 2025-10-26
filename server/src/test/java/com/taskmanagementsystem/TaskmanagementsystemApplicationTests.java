package com.taskmanagementsystem;

import com.taskmanagementsystem.dto.TaskDTO;
import com.taskmanagementsystem.exception.TaskNotFoundException;
import com.taskmanagementsystem.mapper.TaskMapper;
import com.taskmanagementsystem.model.Task;
import com.taskmanagementsystem.model.TaskStatus;
import com.taskmanagementsystem.repository.TaskRepository;
import com.taskmanagementsystem.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
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
	void getAllTasks_mapsPage() {
		Pageable incoming = PageRequest.of(0, 10);

		Page<Task> page = new PageImpl<>(List.of(task), incoming, 1);

		when(taskRepository.findByStatusNot(eq(TaskStatus.DONE), any(Pageable.class)))
				.thenReturn(page);
		when(taskMapper.mapToDTO(task)).thenReturn(dto);

		Page<TaskDTO> result = taskService.getAllTasks(incoming);

		assertEquals(1, result.getTotalElements());
		assertEquals("Test Task", result.getContent().get(0).getTitle());

		ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
		verify(taskRepository).findByStatusNot(eq(TaskStatus.DONE), pageableCaptor.capture());
		Pageable used = pageableCaptor.getValue();

		assertEquals(5, used.getPageSize());
		assertEquals(0, used.getPageNumber());
		assertNotNull(used.getSort().getOrderFor("createdDate"));

		verify(taskMapper).mapToDTO(task);
		verifyNoMoreInteractions(taskRepository, taskMapper);
	}

	@Test
	void markTaskAsCompleted_setsDone_whenFound() {
		when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
		when(taskRepository.save(any(Task.class))).thenAnswer(inv -> inv.getArgument(0));
		when(taskMapper.mapToDTO(any(Task.class))).thenReturn(dto);

		TaskDTO result = taskService.markTaskAsCompleted(1L);

		assertThat(result).isNotNull();
		assertEquals(TaskStatus.DONE, task.getStatus());
		verify(taskRepository).findById(1L);
		verify(taskRepository).save(task);
		verify(taskMapper).mapToDTO(task);
	}

	@Test
	void markTaskAsCompleted_throws_whenNotFound() {
		when(taskRepository.findById(99L)).thenReturn(Optional.empty());

		assertThrows(TaskNotFoundException.class, () -> taskService.markTaskAsCompleted(99L));

		verify(taskRepository).findById(99L);
		verify(taskRepository, never()).save(any());
		verifyNoInteractions(taskMapper);
	}
}
