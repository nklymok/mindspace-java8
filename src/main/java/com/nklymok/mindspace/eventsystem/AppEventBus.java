package com.nklymok.mindspace.eventsystem;

import com.google.common.eventbus.EventBus;
import lombok.Getter;

public class AppEventBus {

    @Getter
    private static AppEventBus instance;
    private static EventBus eventBus;

    static {
        instance = new AppEventBus();
        eventBus = new EventBus();
    }

    protected AppEventBus() {

    }

    public static void register(Subscriber subscriber) {
        eventBus.register(subscriber);
    }

    public static void post(Postable event) {
        eventBus.post(event);
    }
}
