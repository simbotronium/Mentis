package com.example.mentis.presentation;

import com.example.mentis.application.DataManager;
import com.example.mentis.business.data.ProjectListEntry;
import com.example.mentis.business.logic.Manager;
import com.example.mentis.business.logic.View;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class ProjectListCell extends ListCell<ProjectListEntry> {

    private final VBox root;
    private final Button button;

    public ProjectListCell() {
        root = new VBox();
        root.setAlignment(Pos.CENTER);
        HBox.setHgrow(root, Priority.ALWAYS);
        button = new Button("ERROR");
        button.getStyleClass().add("primary-button");
        button.setPrefWidth(300);
        root.getChildren().add(button);
    }

    @Override
    protected void updateItem(ProjectListEntry projectListEntry, boolean empty) {
        super.updateItem(projectListEntry, empty);

        if (empty || projectListEntry == null) {
            setGraphic(null);
            setText(null);
        } else {
            button.setText(projectListEntry.name());
            button.setOnAction(e -> {
                Manager.getInstance().currentProjectProperty().set(DataManager.getProjectById(projectListEntry.projectId()));
                Manager.getInstance().currentViewProperty().set(View.PROJECT);
            });
            setGraphic(root);
            setText(null);
        }
    }

}
