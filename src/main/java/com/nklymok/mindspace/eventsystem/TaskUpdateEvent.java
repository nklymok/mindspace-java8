package com.nklymok.mindspace.eventsystem;

import com.nklymok.mindspace.model.TaskModel;
import lombok.Getter;

public class TaskUpdateEvent implements Postable {

    @Getter
    private final TaskModel model;

    public TaskUpdateEvent(TaskModel model) {
        this.model = model;
    }
}
