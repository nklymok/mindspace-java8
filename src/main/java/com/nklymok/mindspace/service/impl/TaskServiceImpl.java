package com.nklymok.mindspace.service.impl;

import com.nklymok.mindspace.model.TaskModel;
import com.nklymok.mindspace.exceptions.EntityNotFoundException;
import com.nklymok.mindspace.exceptions.EntityNotSavedException;
import com.nklymok.mindspace.repository.TaskRepository;
import com.nklymok.mindspace.repository.impl.TaskRepositoryImpl;
import com.nklymok.mindspace.service.TaskService;

import java.util.List;

public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;

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
    public TaskModel findById(Long id) {
        return taskRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("TaskModel with id \"%d\" not found.", id)));
    }

    @Override
    public List<TaskModel> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public TaskModel save(TaskModel task) {
        return taskRepository.save(task).orElseThrow(
                () -> new EntityNotSavedException("TaskModel could not be saved"));
    }

    @Override
    public TaskModel update(TaskModel task) {
        return taskRepository.update(task).orElseThrow(
        () -> new EntityNotFoundException("TaskModel could not be found."));
    }

    @Override
    public void delete(TaskModel task) {
        taskRepository.delete(task);
    }

    @Override
    public void deleteById(Long id) {
        taskRepository.deleteById(id);
    }
}
