package com.nklymok.mindspace.view.effect;

import javafx.scene.Node;

public class ErrorEffect {

    private static final String ERROR_STYLES = "txtfield-error";

    public static void set(Node node) {
        node.getStyleClass().add(ERROR_STYLES);
    }

    public static void remove(Node node) {
        node.getStyleClass().removeAll(ERROR_STYLES);
    }
}
