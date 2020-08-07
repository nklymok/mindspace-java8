package com.nklymok.mindspace.repository.impl;

import com.nklymok.mindspace.connection.ConnectionManager;
import com.nklymok.mindspace.model.TaskModel;
import com.nklymok.mindspace.repository.TaskRepository;
import org.apache.log4j.Logger;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TaskRepositoryImpl implements TaskRepository {

    private static final Logger logger = Logger.getLogger(TaskRepositoryImpl.class);
    private static final String SAVE_QUERY = "INSERT INTO tasks(header, description, duedate, priority) VALUES(?,?,?,?)";
    private static final String DELETE_QUERY = "DELETE FROM tasks WHERE id = ?";
    private static final String SELECT_QUERY = "SELECT * FROM tasks WHERE id = ?";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM tasks";
    private static final String UPDATE_QUERY = "UPDATE tasks " +
            "SET header = ?, description = ?, duedate = ?, priority = ? WHERE id = ?";

    private final Connection CONNECTION;

    private TaskRepositoryImpl() {
        CONNECTION = ConnectionManager.openConnection();
    }

    private static class TaskRepositoryImplHelper {
        private static final TaskRepositoryImpl INSTANCE = new TaskRepositoryImpl();
    }

    public static TaskRepositoryImpl getInstance() {
        return TaskRepositoryImplHelper.INSTANCE;
    }

    @Override
    public Optional<TaskModel> findById(Long id) {
        TaskModel task = null;

        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(SELECT_QUERY)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                task = TaskModel
                        .builder()
                        .id(resultSet.getLong(1))
                        .header(resultSet.getString(2))
                        .description(resultSet.getString(3))
                        .dueDate(resultSet.getTimestamp(4).toLocalDateTime())
                        .priority(resultSet.getInt(5))
                        .build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(task);
    }

    @Override
    public List<TaskModel> findAll() {
        List<TaskModel> taskList = new ArrayList<>();
        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(SELECT_ALL_QUERY)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                taskList.add(TaskModel.builder()
                .id(resultSet.getLong(1))
                .header(resultSet.getString(2))
                .description(resultSet.getString(3))
                .dueDate(resultSet.getTimestamp(4).toLocalDateTime())
                .priority(resultSet.getInt(5))
                .build());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return taskList;
    }

    @Override
    public Optional<TaskModel> save(TaskModel task) {
        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(SAVE_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, task.getHeader());
            preparedStatement.setString(2, task.getDescription());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(task.getDueDate()));
            preparedStatement.setInt(4, task.getPriority());
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                task.setId(generatedKeys.getLong(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(task);
    }

    @Override
    public Optional<TaskModel> update(TaskModel task) {
        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(UPDATE_QUERY)) {
            preparedStatement.setString(1, task.getHeader());
            preparedStatement.setString(2, task.getDescription());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(task.getDueDate()));
            preparedStatement.setInt(4, task.getPriority());
            preparedStatement.setLong(5, task.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return Optional.ofNullable(task);
    }

    @Override
    public void delete(TaskModel task) {
        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(DELETE_QUERY)) {
            preparedStatement.setLong(1, task.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(Long id) {
        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(DELETE_QUERY)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}