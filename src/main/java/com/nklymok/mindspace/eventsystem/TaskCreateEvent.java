package com.nklymok.mindspace.eventsystem;

import com.nklymok.mindspace.model.TaskModel;
import javafx.scene.Parent;
import lombok.Getter;

public class TaskCreateEvent implements Postable {

    @Getter
    private final TaskModel model;
    @Getter
    private Parent taskParent;
    @Getter
    private Parent recentParent;

    public TaskCreateEvent(TaskModel model) {
        this.model = model;
    }

    public TaskCreateEvent(TaskModel model, Parent taskParent, Parent recentParent) {
        this.model = model;
        this.taskParent = taskParent;
        this.recentParent = recentParent;
    }
}
