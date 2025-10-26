package com.taskmanagementsystem;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taskmanagementsystem.dto.TaskDTO;
import com.taskmanagementsystem.model.Task;
import com.taskmanagementsystem.model.TaskStatus;
import com.taskmanagementsystem.repository.TaskRepository;
import com.taskmanagementsystem.service.TaskService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TaskRepository taskRepository;

    private TaskDTO sampleTask;

    private TaskDTO dto(String title, String desc, TaskStatus status) {
        TaskDTO t = new TaskDTO();
        t.setTitle(title);
        t.setDescription(desc);
        t.setStatus(status);
        return t;
    }

    @BeforeEach
    void setUp() {
        sampleTask = new TaskDTO();
        sampleTask.setTitle("Sample Task");
        sampleTask.setDescription("Sample Description");
        sampleTask.setStatus(TaskStatus.NOT_DONE);
    }

    @Test
    void testCreateTask() throws Exception {
        // Arrange
        long before = taskRepository.count();

        // Act
        String responseJson = mockMvc.perform(
                post("/tasks") 
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleTask)))
                // Assert HTTP response
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").value("Sample Task"))
                .andExpect(jsonPath("$.status").value("NOT_DONE"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        TaskDTO created = objectMapper.readValue(responseJson, TaskDTO.class);
        assertThat(created.getId()).isNotNull();

        long after = taskRepository.count();
        assertThat(after).isEqualTo(before + 1);

        Task saved = taskRepository.findById(created.getId()).orElseThrow();
        assertThat(saved.getTitle()).isEqualTo("Sample Task");
        assertThat(saved.getDescription()).isEqualTo("Sample Description");
        assertThat(saved.getStatus()).isEqualTo(TaskStatus.NOT_DONE);
    }

    @Test
    void testGetAllTasks() throws Exception {
        mockMvc.perform(post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto("Sample Task A", "Desc A", TaskStatus.NOT_DONE))))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto("Sample Task B", "Desc B", TaskStatus.DONE))))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/tasks?page=0&size=5&sort=id,desc"))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.number", is(0)))
                .andExpect(jsonPath("$.size", is(5)))
                .andExpect(jsonPath("$.totalElements", greaterThanOrEqualTo(2)))
                .andExpect(jsonPath("$.totalPages", greaterThanOrEqualTo(1)))

                .andExpect(jsonPath("$.content", not(empty())))
                .andExpect(jsonPath("$.content[0].id", notNullValue()))
                .andExpect(jsonPath("$.content[0].title", notNullValue()));

    }

    @Test
    void markTaskAsCompleted_HappyPath_Returns200_AndPersistsDone() throws Exception {
        String createJson = objectMapper.writeValueAsString(dto(
                "Mark Me", "To be completed", TaskStatus.NOT_DONE));

        String createResp = mockMvc.perform(post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.status", is("NOT_DONE")))
                .andReturn()
                .getResponse()
                .getContentAsString();

        TaskDTO created = objectMapper.readValue(createResp, TaskDTO.class);
        Long id = created.getId();

        mockMvc.perform(patch("/tasks/{id}/markCompleted", id))
                .andExpect(status().isOk())
                // .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(id.intValue())))
                .andExpect(jsonPath("$.status", is("DONE")));

        Task saved = taskRepository.findById(id).orElseThrow();
        assertThat(saved.getStatus()).isEqualTo(TaskStatus.DONE);

        mockMvc.perform(get("/tasks/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id.intValue())))
                .andExpect(jsonPath("$.status", is("DONE")));
    }

    @Test
    void markTaskAsCompleted_NonExistingId_Returns404() throws Exception {
        mockMvc.perform(patch("/tasks/{id}/markCompleted", 9_999_999L))
                .andExpect(status().isNotFound());
    }

}
