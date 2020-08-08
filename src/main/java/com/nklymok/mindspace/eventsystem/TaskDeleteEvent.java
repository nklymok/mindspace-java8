package com.nklymok.mindspace.eventsystem;

import com.nklymok.mindspace.model.TaskModel;
import lombok.Getter;

public class TaskDeleteEvent implements Postable {

    @Getter
    private TaskModel model;

    public TaskDeleteEvent(TaskModel model) {
        this.model = model;
    }
}

