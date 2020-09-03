package com.nklymok.mindspace.concurrent;

import com.nklymok.mindspace.connection.ConnectionManager;
import com.nklymok.mindspace.eventsystem.AppEventBus;
import com.nklymok.mindspace.eventsystem.TaskDeleteEvent;
import com.nklymok.mindspace.model.TaskModel;
import com.nklymok.mindspace.service.TaskService;

public class DeadlineThread extends Thread {
    private final TaskService taskService;

    public DeadlineThread(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public void run() {
        while (!ConnectionManager.isClosed()) {
            try {
                sleep(500);
                if (!ConnectionManager.isClosed()) {
                    taskService
                            .findAll()
                            .stream()
                            .filter(TaskModel::isExpired).forEach((e -> AppEventBus.post(new TaskDeleteEvent(e))));
                }
            } catch (InterruptedException e) {
                e.getMessage();
            }
        }
    }
}
