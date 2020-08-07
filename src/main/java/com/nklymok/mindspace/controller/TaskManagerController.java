package com.nklymok.mindspace.controller;

import com.nklymok.mindspace.exceptions.EntityNotFoundException;
import com.nklymok.mindspace.model.TaskModel;
import com.nklymok.mindspace.service.TaskService;
import com.nklymok.mindspace.service.impl.TaskServiceImpl;

import java.util.HashMap;
import java.util.Map;

public class TaskManagerController {
    private TaskService taskService;
    private Map<TaskPaneController, RecentItemPaneController> taskToRecent;
    private SandboxPaneController sandboxPaneController;
    private RecentsPaneController recentsPaneController;

    public TaskManagerController() {
        taskToRecent = new HashMap<>();
        taskService = TaskServiceImpl.getInstance();
    }

    private static class TaskManagerControllerHelper {
        private static final TaskManagerController instance = new TaskManagerController();
    }

    public static TaskManagerController getInstance() {
        return TaskManagerControllerHelper.instance;
    }

    public void setSandboxPane(SandboxPaneController sandboxPaneController) {
        this.sandboxPaneController = sandboxPaneController;
    }

    public void setRecentsPane(RecentsPaneController recentsPaneController) {
        this.recentsPaneController = recentsPaneController;
    }

    public void add(TaskPaneController taskPaneController, RecentItemPaneController recentItemPaneController) {
        taskToRecent.put(taskPaneController, recentItemPaneController);
    }

    public void remove(TaskPaneController taskPaneController) {
        removeByModel(taskPaneController.getModel());
    }

    public void remove(RecentItemPaneController recentItemPaneController) {
        removeByModel(recentItemPaneController.getModel());
    }

    public void removeByModel(TaskModel model) {
        TaskPaneController key = null;
        for (Map.Entry<TaskPaneController, RecentItemPaneController> item : taskToRecent.entrySet()) {
            if (item.getKey().getModel().equals(model)) {
                key = item.getKey();
                break;
            }
        }

        if (key == null) {
            throw new EntityNotFoundException("key is null");
        }
        taskToRecent.remove(key);
        taskService.delete(model);
        sandboxPaneController.removeTask(model);
        recentsPaneController.removeRecent(model);
    }
}
