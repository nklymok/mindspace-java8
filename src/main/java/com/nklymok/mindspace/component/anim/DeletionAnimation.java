package com.nklymok.mindspace.component.anim;

import animatefx.animation.FadeOutRight;
import javafx.scene.Node;

public class DeletionAnimation extends FadeOutRight {
    public DeletionAnimation(Node node) {
        super(node);
        setSpeed(5d);
    }
}
