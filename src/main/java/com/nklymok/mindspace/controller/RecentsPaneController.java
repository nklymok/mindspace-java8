package com.nklymok.mindspace.controller;

import com.google.common.eventbus.Subscribe;
import com.nklymok.mindspace.component.anim.CreationAnimation;
import com.nklymok.mindspace.component.anim.DeletionAnimation;
import com.nklymok.mindspace.eventsystem.*;
import com.nklymok.mindspace.model.TaskModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class RecentsPaneController implements Initializable, Subscriber {
    private final Map<TaskModel, Node> modelToNode;

    @FXML
    VBox recentsPane;

    public RecentsPaneController() {
        modelToNode = new HashMap<>();
    }

    private void addRecent(Node recentNode, TaskModel taskModel) {
        modelToNode.put(taskModel, recentNode);
        recentsPane.getChildren().add(recentNode);
        modelToNode.get(taskModel);
        updateRecentPosition(taskModel);
    }

    private void removeRecent(TaskModel model) {
        recentsPane.getChildren().remove(modelToNode.get(model));
        modelToNode.remove(model);
    }

    // 1. ask for model
    // 2. get the node index
    // 3. check the next node
    // 4. if wrong position - send to that node's position (if next -> toFront(), if previous -> toBack());
    // 5. repeat from step 3 with previous nodes
    private void updateRecentPosition(TaskModel model) {
        if (recentsPane.getChildren().size() < 2) {
            return;
        }

        if (modelToNode.containsKey(model)) {
            ObservableList<Node> nodes = FXCollections.observableArrayList(recentsPane.getChildren());
            Node recentNode = modelToNode.get(model);
            int index = getIndexOf(recentNode);


            // sorts recentsPane elements from the element to the top of the list
            for (int i = index - 1; i >= 0; i--) {
                int comparison = model.compareTo(getModelOf(nodes.get(i)));
                if (comparison > 0) {
                    Collections.swap(nodes, index, i);
                    index = i;
                } else {
                    break;
                }
            }

            // sorts recentsPane elements from the element to the bottom of the list
            for (int i = index + 1; i < nodes.size(); i++) {
                int comparison = model.compareTo(getModelOf(nodes.get(i)));
                if (comparison < 0) {
                    Collections.swap(nodes, index, i);
                    index = i;
                } else {
                    break;
                }
            }

            recentsPane.getChildren().setAll(nodes);
        }
    }

    private TaskModel getModelOf(Node node) {
        for (Map.Entry<TaskModel, Node> item : modelToNode.entrySet()) {
            if (item.getValue().equals(node)) {
                return item.getKey();
            }
        }

        return null;
    }

    private int getIndexOf(Node node) {
        int index = 0;
        for (Node item : recentsPane.getChildren()) {
            if (node.equals(item)) {
                return index;
            }

            index++;
        }

        return -1;
    }

    @Subscribe
    private void handleTaskCreateEvent(TaskCreateEvent event) {
        if (event.getTaskParent() != null) {
            addRecent(event.getRecentParent(), event.getModel());
            new CreationAnimation(event.getRecentParent()).play();
        }
    }

    @Subscribe
    private void handleTaskDeleteEvent(TaskDeleteEvent event) {
        TaskModel eventModel = event.getModel();
        DeletionAnimation deletionAnimation = new DeletionAnimation(modelToNode.get(eventModel));
        deletionAnimation.setOnFinished(e -> removeRecent(eventModel));
        deletionAnimation.play();
    }

    @Subscribe
    private void handleTaskUpdateEvent(TaskUpdateEvent event) {
        updateRecentPosition(event.getModel());
    }

    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        AppEventBus.register(this);
    }
}
