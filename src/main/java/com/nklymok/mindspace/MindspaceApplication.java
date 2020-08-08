package com.nklymok.mindspace;

import com.nklymok.mindspace.eventsystem.AppEventBus;
import com.nklymok.mindspace.eventsystem.TaskCreateEvent;
import com.nklymok.mindspace.model.TaskModel;
import com.nklymok.mindspace.service.TaskService;
import com.nklymok.mindspace.service.impl.TaskServiceImpl;
import com.nklymok.mindspace.view.effect.BlurEffect;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MindspaceApplication extends Application {
    private final double DEFAULT_WIDTH = 800d;
    private final double DEFAULT_HEIGHT = 600d;

    TaskService taskService;

    private Parent root;
    private Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        root = loader.load(getClass().getResource("/fxmls/root-pane.fxml").openStream());
        BlurEffect.setTarget(root);
        stage = primaryStage;
        Scene scene = new Scene(root, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        stage.setScene(scene);
        stage.setMinWidth(DEFAULT_WIDTH);
        stage.setMinHeight(DEFAULT_HEIGHT);
        stage.show();
    }

    public void initialize() {
        taskService = TaskServiceImpl.getInstance();
        createPreviouslyExistedTasks();
    }

    public void createPreviouslyExistedTasks() {
        for (TaskModel taskModel : taskService.findAll()) {
            AppEventBus.post(new TaskCreateEvent(taskModel));
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}