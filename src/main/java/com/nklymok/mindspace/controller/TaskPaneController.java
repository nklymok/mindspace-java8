package com.nklymok.mindspace.controller;

import animatefx.animation.AnimationFX;
import animatefx.animation.FadeInRight;
import com.google.common.eventbus.Subscribe;
import com.nklymok.mindspace.component.Animations;
import com.nklymok.mindspace.eventsystem.*;
import com.nklymok.mindspace.model.TaskModel;
import com.nklymok.mindspace.service.TaskService;
import com.nklymok.mindspace.service.impl.TaskServiceImpl;
import com.nklymok.mindspace.view.effect.BlurEffect;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class TaskPaneController implements Initializable, Subscriber {

    private final TaskService taskService;

    private TaskModel model;

    @FXML
    private AnchorPane rootPane;
    @FXML
    private PriorityPaneController priorityPane;
    @FXML
    private Text headerText;
    @FXML
    private Text descriptionText;
    @FXML
    private Button editButton;
    @FXML
    private Text dueText;

    private double xBoundary = 0.0d;
    private double yBoundary = 0.0d;
    private double mouseX = 0.0d;
    private double mouseY = 0.0d;

    private final EventHandler<ActionEvent> editButtonHandler = event -> {
        edit();
    };

    private final EventHandler<MouseEvent> mousePressHandler = e -> {
        rootPane.toFront();
        mouseX = e.getX();
        mouseY = e.getY();
    };

    private final EventHandler<MouseEvent> mouseDragHandler = e -> {
        double x = e.getX() + rootPane.getTranslateX() - mouseX,
                y = e.getY() + rootPane.getTranslateY() - mouseY;
        rootPane.setTranslateX(x);
        rootPane.setTranslateY(y);
        e.consume();
    };

    private final EventHandler<MouseEvent> mouseReleaseHandler = Event::consume;

    private final double ANIMATION_SPEED = 5;

    public TaskPaneController(TaskModel taskModel) {
        taskService = TaskServiceImpl.getInstance();
        model = taskModel;
    }

    private void delete() {
        taskService.delete(model);
    }

    private void edit() {
        FXMLLoader editStageFXMLLoader = new FXMLLoader(getClass().getResource("/fxmls/edit-stage.fxml"));
        EditStageController editStageController = new EditStageController(model);
        editStageFXMLLoader.setController(editStageController);

        Stage stage = new Stage(StageStyle.TRANSPARENT);
        stage.setAlwaysOnTop(true);
        try {
            Scene editScene = new Scene(editStageFXMLLoader.load());
            editScene.setFill(Color.TRANSPARENT);
            stage.setScene(editScene);
            stage.setOnShown(event -> BlurEffect.getInstance().blur());
            stage.setOnHidden(event -> BlurEffect.getInstance().unblur());
            stage.setAlwaysOnTop(true);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subscribe
    private void handleTaskUpdateEvent(TaskUpdateEvent event) {
        TaskModel eventModel = event.getModel();
        if (this.model.getId().equals(eventModel.getId())) {
            this.model = eventModel;
            updateFields();
        }
    }

    @Subscribe
    private void handleTaskDeleteEvent(TaskDeleteEvent event) {
        TaskModel eventModel = event.getModel();
        if (this.model.getId().equals(eventModel.getId())) {
            delete();
        }
    }

    private void updateFields() {
        taskService.update(model);
        updateHeader();
        updateDescription();
        updateDueDate();
        updatePriority();
    }

    private void updateHeader() {
        headerText.setText(model.getHeader());
    }

    private void updateDescription() {
        descriptionText.setText(model.getDescription());
    }

    private void updateDueDate() {
        dueText.setText(model.getDueDate().format(DateTimeFormatter.ofPattern("dd MMM uuuu HH:mm")));
    }

    private void updatePriority() {
        priorityPane.setPriority(model.getPriority());
    }

    public TaskModel getModel() {
        return model;
    }

    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        AppEventBus.register(this);

        editButton.setOnAction(editButtonHandler);
        rootPane.setOnMouseDragged(mouseDragHandler);
        rootPane.setOnMousePressed(mousePressHandler);
        rootPane.setOnMouseReleased(mouseReleaseHandler);
        updateFields();
    }
}