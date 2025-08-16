package com.example.mentis.business.logic;

public enum View {

    MAIN_MENU("/com/example/mentis/views/main-menu-view.fxml"),
    PROJECT("/com/example/mentis/views/project-view.fxml"),
    PROJECT_SETTINGS("/com/example/mentis/views/project-settings-view.fxml"),
    MEMBER("/com/example/mentis/views/member-view.fxml"),
    EXAM("/com/example/mentis/views/exam-view.fxml");

    private final String fxmlPath;

    private View(final String fxmlPath) {
        this.fxmlPath = fxmlPath;
    }

    public String getFxmlPath() {
        return fxmlPath;
    }
}
