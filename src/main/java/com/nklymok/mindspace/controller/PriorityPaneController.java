package com.nklymok.mindspace.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

/**
 * A colored indicator on tasks, recents and in TaskBuilder.
 * @author Nazarii Klymok
 */
public class PriorityPaneController extends Pane implements Comparable<PriorityPaneController> {
    private int priority;

    /**
     * Initialized with a default priority.
     */
    public PriorityPaneController() {
        this(-1);
    }

    /**
     * Initialized with a chosen priority.
     * @param p the priority to be used
     */
    public PriorityPaneController(int p) {
        priority = p;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmls/priority-pane.fxml"));
        loader.setController(this);
        loader.setRoot(this);
        try {
            loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the priority for the indicator, automatically changes styles.
     * 4 states:
     * default = -1
     * low = 0
     * medium = 1
     * high = 2
     * @param p the priority to be set
     */
    public void setPriority(int p) {
        this.priority = p;
        updateStyles();
    }

    /**
     * Sets the priority of the passed PriorityPaneController.
     * @param p PriorityPaneController to be used
     */
    public void setPriority(PriorityPaneController p) {
        priority = p.getIntValue();
        updateStyles();
    }

    /**
     * 4 states:
     * default = -1
     * low = 0
     * medium = 1
     * high = 2
     * @return the priority of the indicator
     */
    public int getIntValue() {
        return priority;
    }

    private void updateStyles() {
        getStyleClass().clear();
        switch (priority) {
            case 0:
                getStyleClass().add("low-priority-indicator");
                break;
            case 1:
                getStyleClass().add("medium-priority-indicator");
                break;
            case 2:
                getStyleClass().add("high-priority-indicator");
                break;
            case -1:
                getStyleClass().add("no-priority-indicator");
                break;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof PriorityPaneController) {
            return this.getIntValue() == ((PriorityPaneController) o).getIntValue();
        }
        else if (o instanceof Integer) {
            return this.getIntValue() == (Integer) o;
        }

        return false;
    }

    public int compareTo(PriorityPaneController p) {
        return Integer.compare(this.getIntValue(), p.getIntValue());
    }
}
