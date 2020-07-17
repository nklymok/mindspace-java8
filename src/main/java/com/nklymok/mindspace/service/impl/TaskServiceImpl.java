package com.nklymok.mindspace.service.impl;

import com.nklymok.mindspace.entity.Task;
import com.nklymok.mindspace.exceptions.EntityNotFoundException;
import com.nklymok.mindspace.exceptions.EntityNotSavedException;
import com.nklymok.mindspace.repository.TaskRepository;
import com.nklymok.mindspace.repository.impl.TaskRepositoryImpl;
import com.nklymok.mindspace.service.TaskService;

import java.util.List;

public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    private TaskServiceImpl() {
        taskRepository = TaskRepositoryImpl.getInstance();
    }

    private static final class TaskServiceImplHelper {
        private static final TaskServiceImpl INSTANCE = new TaskServiceImpl();
    }

    public static TaskServiceImpl getInstance() {
        return TaskServiceImplHelper.INSTANCE;
    }

    @Override
    public Task findById(Long id) {
        return taskRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Task with id \"%d\" not found.", id)));
    }

    @Override
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public Task save(Task task) {
        return taskRepository.save(task).orElseThrow(
                () -> new EntityNotSavedException("Task could not be saved"));
    }

    @Override
    public Task update(Task task) {
        return taskRepository.update(task).orElseThrow(
        () -> new EntityNotFoundException("Task could not be found."));
    }

    @Override
    public void delete(Task task) {
        taskRepository.delete(task);
    }

    @Override
    public void deleteById(Long id) {
        taskRepository.deleteById(id);
    }
}
