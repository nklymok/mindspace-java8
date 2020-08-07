package com.nklymok.mindspace.controller;

import javafx.fxml.FXML;

public class SidePaneController {
    @FXML
    TaskBuilderPaneController taskBuilderPaneController;
    @FXML
    RecentsPaneController recentsPaneController;

    public TaskBuilderPaneController getTaskBuilderPaneController() {
        return taskBuilderPaneController;
    }

    public RecentsPaneController getRecentsPaneController() {
        return recentsPaneController;
    }
}
