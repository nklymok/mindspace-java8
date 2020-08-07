package com.nklymok.mindspace.model;

import lombok.*;

import java.time.LocalDateTime;

@Data
public class TaskModel implements Comparable<TaskModel> {
    private Long id;
    private String header;
    private String description;
    private LocalDateTime dueDate;
    private Integer priority;

    @Builder
    public TaskModel(Long id, String header, String description, LocalDateTime dueDate, Integer priority) {
        this.id = id;
        this.header = header;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
    }

    @Override
    public int compareTo(TaskModel taskModel) {
        int comparisonResult = this.getPriority().compareTo(taskModel.getPriority());
        if (comparisonResult == 0) {
            return this.getDueDate().compareTo(taskModel.getDueDate());
        }

        return comparisonResult;
    }
}
