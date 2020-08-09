package com.nklymok.mindspace.eventsystem;

import com.nklymok.mindspace.component.Animations;
import com.nklymok.mindspace.model.TaskModel;
import lombok.Getter;

public class TaskAnimationEvent implements Postable {

    @Getter
    private final TaskModel model;
    @Getter
    private final Animations animation;

    public TaskAnimationEvent(TaskModel model, Animations animation) {
        this.model = model;
        this.animation = animation;
    }
}
