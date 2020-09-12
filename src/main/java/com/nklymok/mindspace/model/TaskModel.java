package com.nklymok.mindspace.model;

import com.google.common.primitives.Longs;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class TaskModel implements Comparable<TaskModel> {
    private Long id;
    private String header;
    private String description;
    private LocalDateTime dueDate;
    private Integer priority;
    private Integer repeats;

    @Builder
    public TaskModel(Long id, String header, String description, LocalDateTime dueDate, Integer priority, Integer repeats) {
        this.id = id;
        this.header = header;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
        this.repeats = repeats;
    }

    public boolean isExpired() {
        return dueDate.toLocalDate().compareTo(LocalDate.now()) < 0;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof TaskModel)) {
            return false;
        }

        return ((TaskModel) o).getId().equals(this.getId());
    }

    @Override
    public int hashCode() {
        return Longs.hashCode(id);
    }

    @Override
    public int compareTo(TaskModel taskModel) {
        if (this.equals(taskModel)) {
            return 0;
        }

        int comparisonResult = this.getPriority().compareTo(taskModel.getPriority());

        if (comparisonResult == 0) {
            return -1;
        }

        return comparisonResult;
    }


}
