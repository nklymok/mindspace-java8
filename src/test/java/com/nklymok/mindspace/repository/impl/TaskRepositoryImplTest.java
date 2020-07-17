package com.nklymok.mindspace.repository.impl;

import com.nklymok.mindspace.connection.ConnectionManager;
import com.nklymok.mindspace.entity.Task;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TaskRepositoryImplTest {

    private TaskRepositoryImpl taskRepositoryImpl;

    @BeforeEach
    public void beforeEach() {
        taskRepositoryImpl = TaskRepositoryImpl.getInstance();
    }

    @Test
    public void save_saveTaskToDB_success() {
        Task task = Task.builder()
                .header("Task Header")
                .description("Task Description")
                .dueDate(LocalDateTime.now())
                .priority(2)
                .build();

        Optional<Task> optionalTask = taskRepositoryImpl.save(task);
        assertTrue(optionalTask.isPresent());
        System.out.println(optionalTask.get().getId());
        assertNotEquals(0, optionalTask.get().getId());
    }

    @Test
    public void findById_findsTaskByIdInDB_success() {
        Task task = Task.builder()
                .header("Task Header")
                .description("Task Description")
                .dueDate(LocalDateTime.now())
                .priority(2)
                .build();
        taskRepositoryImpl.save(task);
        assertNotNull(taskRepositoryImpl.findById(task.getId()));
        assertNotEquals(0, task.getId());
    }

    @Test
    public void findAll_findsAllTasksInDB_success() {
        for (Task t : taskRepositoryImpl.findAll()) {
            assertNotNull(taskRepositoryImpl.findById(t.getId()));
            assertNotEquals(0, t.getId());
        }
    }

    @Test
    public void delete_deleteTaskFromDB_success() {
        Task task = Task.builder()
                .header("Task Header")
                .description("Task Description")
                .dueDate(LocalDateTime.now())
                .priority(2)
                .build();

        Optional<Task> optionalTask = taskRepositoryImpl.save(task);
        assertTrue(optionalTask.isPresent());
        taskRepositoryImpl.delete(task);
        assertFalse(taskRepositoryImpl.findById(optionalTask.get().getId()).isPresent());
    }

    @Test
    public void deleteById_deleteTaskByIdInDB_success() {
        Task task = Task.builder()
                .header("Task Header")
                .description("Task Description")
                .dueDate(LocalDateTime.now())
                .priority(2)
                .build();

        Optional<Task> optionalTask = taskRepositoryImpl.save(task);
        assertTrue(optionalTask.isPresent());
        taskRepositoryImpl.deleteById(task.getId());
        assertFalse(taskRepositoryImpl.findById(optionalTask.get().getId()).isPresent());
    }

    @Test
    public void update_updatesTaskInDB_success() {
        Optional<Task> taskOptional = taskRepositoryImpl.findById(2L);
        assertTrue(taskOptional.isPresent());
        taskOptional.get().setHeader("Updated task");
        taskRepositoryImpl.update(taskOptional.get());
        taskOptional = taskRepositoryImpl.findById(2L);
        assertTrue(taskOptional.isPresent());
        assertEquals("Updated task", taskOptional.get().getHeader());
    }

    @AfterAll
    static void afterAll() {
        ConnectionManager.closeConnection();
    }

}