package com.nklymok.mindspace.eventsystem;

import com.nklymok.mindspace.model.TaskModel;
import lombok.Getter;

public class TaskEditEvent implements Postable {

    @Getter
    private final TaskModel model;

    public TaskEditEvent(TaskModel model) {
        this.model = model;
    }
}
