package com.nklymok.mindspace.eventsystem;

import animatefx.animation.*;
import com.nklymok.mindspace.component.Animations;
import com.nklymok.mindspace.model.TaskModel;
import javafx.scene.shape.Rectangle;
import lombok.Getter;

public class TaskAnimationEvent implements Postable {

    @Getter
    private final TaskModel model;
    @Getter
    private final AnimationFX animation;

    public TaskAnimationEvent(TaskModel model, Animations animEnum) {
        this.model = model;
        switch (animEnum) {
            case CREATION:
                // initializing with a dummy value because a node is required to initialize a timeline
                animation = new FadeInLeft(new Rectangle());
                break;
            default:
                animation = null;
                break;
        }
    }
}
