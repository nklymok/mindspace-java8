package com.nklymok.mindspace.eventsystem;

import lombok.Getter;

public class BackgroundUpdateEvent implements Postable {

    @Getter
    private String address;

    public BackgroundUpdateEvent(String address) {
        this.address = address;
    }
}
