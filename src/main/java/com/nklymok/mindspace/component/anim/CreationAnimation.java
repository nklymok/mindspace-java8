package com.nklymok.mindspace.component.anim;

import animatefx.animation.FadeInLeft;
import javafx.scene.Node;

public class CreationAnimation extends FadeInLeft {
    public CreationAnimation(Node node) {
        super(node);
        setSpeed(5d);
    }
}
