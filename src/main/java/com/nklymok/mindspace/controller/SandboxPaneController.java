package com.nklymok.mindspace.controller;

import com.google.common.eventbus.Subscribe;
import com.nklymok.mindspace.component.anim.CreationAnimation;
import com.nklymok.mindspace.component.anim.DeletionAnimation;
import com.nklymok.mindspace.eventsystem.*;
import com.nklymok.mindspace.model.TaskModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class SandboxPaneController implements Initializable, Subscriber {
    private final Map<TaskModel, Node> modelToNode;

    @FXML
    private BorderPane sandboxPane;

    private final double TASK_WIDTH = 250;
    private final double TASK_HEIGHT = 100;

    public SandboxPaneController() {
        modelToNode = new HashMap<>();
    }

    private void addTask(Node taskNode, TaskModel taskModel) {
        modelToNode.put(taskModel, taskNode);
        sandboxPane.getChildren().add(taskNode);

        // setting positioning listeners to protect from leaving the scene boundaries
        taskNode.translateXProperty().addListener(e -> relocateTaskNode(taskNode));
        taskNode.translateYProperty().addListener(e -> relocateTaskNode(taskNode));
    }

    private void removeTask(TaskModel model) {
        sandboxPane.getChildren().remove(modelToNode.get(model));
        modelToNode.remove(model);
    }

    @Subscribe
    private void handleTaskCreateEvent(TaskCreateEvent event) {
        if (event.getTaskParent() != null) {
            addTask(event.getTaskParent(), event.getModel());
            new CreationAnimation(event.getTaskParent()).play();
        }
    }

    @Subscribe
    private void handleTaskDeleteEvent(TaskDeleteEvent event) {
        TaskModel eventModel = event.getModel();
        DeletionAnimation deletionAnimation = new DeletionAnimation(modelToNode.get(eventModel));
        deletionAnimation.setOnFinished(e -> removeTask(eventModel));
        deletionAnimation.play();
    }

    @Subscribe
    private void handleBackgroundUpdateEvent(BackgroundUpdateEvent event) {
        String address = event.getAddress();
        setBackground(event.getAddress());
    }

    private void setBackground(String address) {
        if (sandboxPane.getStyleClass().size() > 1) {
            sandboxPane.getStyleClass().remove(1);
        }

        if (address == null) {
            return;
        }

        sandboxPane.getStyleClass().add(address);
        Preferences prefs = Preferences.userRoot();
        String bg = prefs.get("bgsandboxpane", null);
        if (bg == null || !bg.equals(address)) {
            Preferences.userRoot().put("bgsandboxpane", address);
        }

        System.out.println(Preferences.userRoot().get("bgsandboxpane", null));
    }

    private void setBackground() {
        System.out.println(sandboxPane.getStyleClass());
        sandboxPane.getStyleClass().add(Preferences.userRoot().get("bgsandboxpane", null));
        System.out.println(sandboxPane.getStyleClass());
    }

    private void relocateTaskNode(Node taskNode) {
        int parameter = legalCheck(taskNode.getTranslateX(), taskNode.getTranslateY());
        switch (parameter) {
            case -1:
                taskNode.setTranslateX(0);
                break;
            case -2:
                taskNode.setTranslateX(sandboxPane.widthProperty().doubleValue() - TASK_WIDTH);
                break;
            case -3:
                taskNode.setTranslateY(0);
                break;
            case -4:
                taskNode.setTranslateY(sandboxPane.heightProperty().doubleValue() - TASK_HEIGHT);
                break;
        }
    }

    private int legalCheck(double x, double y) {
        if (sandboxPane.widthProperty().doubleValue() <= 0 || sandboxPane.heightProperty().doubleValue() <= 0) {
            return 0;
        }

        if (x < 0) {
            return -1;
        }

        if (x > sandboxPane.widthProperty().doubleValue() - TASK_WIDTH) {
            return -2;
        }

        if (y < 0) {
            return -3;
        }

        if (y > sandboxPane.heightProperty().doubleValue() - TASK_HEIGHT) {
            return -4;
        }

        return 0;
    }

    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        AppEventBus.register(this);

        sandboxPane.widthProperty().addListener(e -> {
            for (Map.Entry<TaskModel, Node> item : modelToNode.entrySet()) {
                relocateTaskNode(item.getValue());
            }
        });
        sandboxPane.heightProperty().addListener(e -> {
            for (Map.Entry<TaskModel, Node> item : modelToNode.entrySet()) {
                relocateTaskNode(item.getValue());
            }
        });

        setBackground();
    }
}
