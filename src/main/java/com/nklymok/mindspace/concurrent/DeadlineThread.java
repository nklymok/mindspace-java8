package com.nklymok.mindspace.concurrent;

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
        while (true) {
            try {
                sleep(1000);
                taskService.findAll().stream().filter(TaskModel::isExpired).forEach((e -> {
                    System.out.println(e.getHeader() + " should be deleted");
                    AppEventBus.post(new TaskDeleteEvent(e));
                }));
            } catch (InterruptedException e) {
                e.getMessage();
            }
        }
    }
}
