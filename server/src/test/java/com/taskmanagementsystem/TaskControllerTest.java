package com.taskmanagementsystem;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taskmanagementsystem.dto.TaskDTO;
import com.taskmanagementsystem.model.Task;
import com.taskmanagementsystem.model.TaskStatus;
import com.taskmanagementsystem.service.TaskService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;

    private TaskDTO sampleTask;

    @BeforeEach
    void setUp() {
        sampleTask = new TaskDTO();
        sampleTask.setTitle("Sample Task");
        sampleTask.setDescription("Sample Description");
        sampleTask.setStatus(TaskStatus.NOT_DONE);
    }

    @Test
    void testCreateTask() throws Exception {
        String json = objectMapper.writeValueAsString(sampleTask);

        mockMvc.perform(post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title", is("Sample Task")))
                .andExpect(jsonPath("$.status", is("NOT_DONE")));
    }

    // ✅ 2. Get Task by ID
    @Test
    void testGetTaskById() throws Exception {
        TaskDTO created = taskService.createTask(sampleTask);

        mockMvc.perform(get("/tasks/" + created.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(created.getId().intValue())))
                .andExpect(jsonPath("$.title", is("Sample Task")));
    }

    // ✅ 3. Update Task
    @Test
    void testUpdateTask() throws Exception {
        TaskDTO created = taskService.createTask(sampleTask);
        created.setTitle("Updated Task");
        created.setDescription("Updated Description");
        created.setStatus(TaskStatus.DONE);

        String updatedJson = objectMapper.writeValueAsString(created);

        mockMvc.perform(put("/tasks/" + created.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Updated Task")))
                .andExpect(jsonPath("$.status", is("DONE")));
    }

    // ✅ 4. Get All Tasks (Pagination)
    @Test
    void testGetAllTasks() throws Exception {
        taskService.createTask(sampleTask);

        mockMvc.perform(get("/tasks?page=0&size=5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", not(empty())))
                .andExpect(jsonPath("$.content[0].title", notNullValue()));
    }

    // ✅ 5. Delete Task
    @Test
    void testDeleteTask() throws Exception {
        TaskDTO created = taskService.createTask(sampleTask);

        mockMvc.perform(delete("/tasks/" + created.getId()))
                .andExpect(status().isNoContent());
    }

}
