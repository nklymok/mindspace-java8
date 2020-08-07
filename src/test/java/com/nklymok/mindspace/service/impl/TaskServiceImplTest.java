package com.nklymok.mindspace.service.impl;

import com.nklymok.mindspace.model.TaskModel;
import com.nklymok.mindspace.repository.impl.TaskRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    private TaskRepositoryImpl taskRepository;
    @InjectMocks
    private TaskServiceImpl taskService;

    @Test
    void save_savesTaskToDB_success() {
//        TaskServiceImpl taskService = new TaskServiceImpl(taskRepository);
        TaskModel expected = TaskModel.builder()
                .header("New TaskPane")
                .description("Description")
                .dueDate(LocalDateTime.now())
                .priority(2)
                .build();
        TaskModel task = TaskModel.builder()
                .header("Saved")
                .description("Saved")
                .dueDate(LocalDateTime.now())
                .priority(1)
                .id(1L)
                .build();
        Mockito.when(taskRepository.save(any())).thenReturn(Optional.ofNullable(expected));
        TaskModel actual = taskService.save(task);
        Mockito.verify(taskRepository).save(any());
        assertEquals(expected, actual);
        System.out.println(actual);
    }
}