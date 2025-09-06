package com.example.mentis.business.logic;

public enum View {

    MAIN_MENU("/com/example/mentis/views/main-menu-view.fxml"),
    PROJECT("/com/example/mentis/views/project-view.fxml"),
    PROJECT_SETTINGS("/com/example/mentis/views/project-settings-view.fxml"),
    PARTICIPANT("/com/example/mentis/views/participant-view.fxml"),
    EXAM("/com/example/mentis/views/exam-view.fxml");

    private final String fxmlPath;

    View(final String fxmlPath) {
        this.fxmlPath = fxmlPath;
    }

    public String getFxmlPath() {
        return fxmlPath;
    }
}
