package com.nklymok.mindspace.service.impl;

import com.nklymok.mindspace.entity.Task;
import com.nklymok.mindspace.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;
//    @InjectMocks
//    private TaskServiceImpl taskService;

    @Test
    void save_savesTaskToDB_success() {
        TaskServiceImpl taskService = new TaskServiceImpl(taskRepository);
        Task expected = Task.builder()
                .header("New Task")
                .description("Description")
                .dueDate(LocalDateTime.now())
                .priority(2)
                .build();
        Task task = Task.builder()
                .header("Saved")
                .description("Saved")
                .dueDate(LocalDateTime.now())
                .priority(1)
                .id(1L)
                .build();
        Mockito.when(taskRepository.save(any())).thenReturn(Optional.ofNullable(expected));
        Task actual = taskService.save(task);
        Mockito.verify(taskRepository).save(any());
        assertEquals(expected, actual);
        System.out.println(actual);
    }
}