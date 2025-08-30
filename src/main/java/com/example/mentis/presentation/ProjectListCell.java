package com.example.mentis.presentation;

import com.example.mentis.application.DataManager;
import com.example.mentis.business.data.ProjectListEntry;
import com.example.mentis.business.logic.Manager;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;

public class ProjectListCell extends ListCell<ProjectListEntry> {

    private Button root;

    public ProjectListCell() {
        root = new Button("ERROR");
        root.getStyleClass().add("primary-button");
    }

    @Override
    protected void updateItem(ProjectListEntry projectListEntry, boolean empty) {
        super.updateItem(projectListEntry, empty);

        if (empty || projectListEntry == null) {
            setGraphic(null);
            setText(null);
        } else {
            root.setText(projectListEntry.name());
            root.setOnAction(e -> Manager.getInstance().currentProjectProperty().set(DataManager.getProjectById(projectListEntry.projectId())));
            setGraphic(root);
            setText(null);
        }
    }

}
