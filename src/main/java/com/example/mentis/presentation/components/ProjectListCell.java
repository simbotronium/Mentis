package com.example.mentis.presentation.components;

import com.example.mentis.application.DataManager;
import com.example.mentis.business.data.Project;
import com.example.mentis.business.data.ProjectListEntry;
import com.example.mentis.business.logic.Manager;
import com.example.mentis.business.logic.View;
import com.example.mentis.presentation.ViewManager;
import com.example.mentis.presentation.transitions.DeleteButtonFadeTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

public class ProjectListCell extends ListCell<ProjectListEntry> {

    private final BorderPane root;
    private final Button button;
    private final Button deleteButton;
    private final DeleteButtonFadeTransition deleteButtonFadeTransition;


    private final Logger log = LoggerFactory.getLogger(ProjectListCell.class);

    public ProjectListCell() {
        root = new BorderPane();
        try {
            root.getStylesheets().add(getClass().getResource("/com/example/mentis/stylesheets/memberListElement.css").toExternalForm());
        } catch (Exception e) {
            log.error("could not find stylesheet: " + e.getMessage());
        }
        deleteButton = new Button("\u2716");
        deleteButton.getStyleClass().add("delete-button");
        deleteButton.setOpacity(0);
        deleteButton.setTooltip(new Tooltip("delete project"));
        deleteButtonFadeTransition = new DeleteButtonFadeTransition(deleteButton);
        Button balanceButton = new Button("\u2716");
        balanceButton.getStyleClass().add("delete-button");
        balanceButton.setVisible(false);
        button = new Button("ERROR");
        button.getStyleClass().add("primary-button");
        button.setPrefWidth(300);

        BorderPane.setAlignment(button, Pos.CENTER);
        BorderPane.setAlignment(deleteButton, Pos.CENTER_RIGHT);
        root.setCenter(button);
        root.setRight(deleteButton);
        root.setLeft(balanceButton);

        this.hoverProperty().addListener(((observable, was, is) -> animateDelete(is)));
    }

    private void animateDelete(boolean show) {
        deleteButtonFadeTransition.play(show);
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
                ViewManager.getInstance().changeView(View.PROJECT);
            });
            deleteButton.setOnAction(e -> DataManager.deleteProject(projectListEntry.projectId()));
            setGraphic(root);
            setText(null);
        }
    }

}
