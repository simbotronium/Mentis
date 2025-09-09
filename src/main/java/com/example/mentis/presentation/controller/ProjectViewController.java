package com.example.mentis.presentation.controller;

import com.example.mentis.business.data.Area;
import com.example.mentis.business.data.Participant;
import com.example.mentis.business.data.Project;
import com.example.mentis.business.logic.ExcelCreator;
import com.example.mentis.business.logic.Manager;
import com.example.mentis.business.logic.View;
import com.example.mentis.presentation.ViewManager;
import com.example.mentis.presentation.components.ParticipantListCell;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class ProjectViewController implements Controller {

    @FXML
    private StackPane root;

    @FXML
    private Label participantLabel;

    @FXML
    private ListView<Participant> participantListView;
    @FXML
    private Label nameLabel;

    private NewParticipantOverlayController overlayController;

    @FXML
    private AnchorPane projectPane;
    @FXML
    private VBox areasBox;
    @FXML
    private Label deviationLabel;
    @FXML
    private Button downloadButton;
    @FXML
    private VBox settingsBox;
    @FXML
    private ImageView editIcon;
    @FXML
    private VBox infoBox;

    private final Manager manager = Manager.getInstance();

    private final ChangeListener<String> nameChangeListener = ((observable, oldValue, newValue) -> nameLabel.setText(newValue));
    private final ListChangeListener<Participant> participantListChangeListener = o -> participantLabel.setText("Participants: " + o.getList().size());
    private final ListChangeListener<Area> areaListChangeListener = o -> createAreaComponents(Manager.getInstance().getCurrentProject().getAreas());
    private final ListChangeListener<String> infoListChangeListener = o -> createFurtherInfos(Manager.getInstance().getCurrentProject().getInfos());
    private final Logger log = LoggerFactory.getLogger(ProjectViewController.class);

    @FXML
    public void initialize() {
        editIcon.setCursor(Cursor.HAND);
        updateView(manager.getCurrentProject());
        setupProjectListener(manager.getCurrentProject(), true);

        manager.currentProjectProperty().addListener((observable, oldValue, newValue) -> {
            log.info("changed project: " + newValue);
            setupProjectListener(oldValue, false);
            setupProjectListener(newValue, true);
            updateView(newValue);
        });

        participantListView.setSelectionModel(null);
        participantListView.visibleProperty().bind(Bindings.isNotEmpty(manager.getCurrentProject().getParticipants()));
        participantListView.managedProperty().bind(participantListView.visibleProperty());
        participantListView.setItems(manager.getCurrentProject().getParticipants());
        participantListView.setCellFactory(list -> new ParticipantListCell());

        if (manager.getCurrentProject() != null) {
            participantLabel.setText("Participants: " + manager.getCurrentProject().getParticipants().size());
            manager.getCurrentProject().getParticipants().addListener(participantListChangeListener);
            manager.getCurrentProject().nameProperty().addListener(nameChangeListener);
        }

    }

    private void setupProjectListener(Project p , boolean add) {
        if (p == null) return;
        if (add) {
            p.nameProperty().addListener(nameChangeListener);
            p.getParticipants().addListener(participantListChangeListener);
            p.getAreas().addListener(areaListChangeListener);
            p.getInfos().addListener(infoListChangeListener);
        } else {
            p.nameProperty().removeListener(nameChangeListener);
            p.getParticipants().removeListener(participantListChangeListener);
            p.getAreas().removeListener(areaListChangeListener);
            p.getInfos().removeListener(infoListChangeListener);
        }
    }

    private void updateView(Project newProject) {
        if (newProject == null) return;
        participantLabel.setText("Participants: " + newProject.getParticipants().size());
        nameLabel.setText(newProject.getName());
        deviationLabel.setText("max. Deviation: " + newProject.getMaxDeviation() + "%");
        createAreaComponents(newProject.getAreas());
        createFurtherInfos(newProject.getInfos());
    }

    private void createFurtherInfos(List<String> infos) {
        infoBox.getChildren().clear();
        for (String info: infos) {
            if (!info.isBlank()) {
                infoBox.getChildren().add(new Label(info));
            }
        }
    }

    private void createAreaComponents(ObservableList<Area> areas) {
        areasBox.getChildren().clear();
        for (Area area: areas) {
            HBox hBox = new HBox();
            Circle circle = new Circle(10);
            circle.setFill(area.color());
            Label label = new Label(area.name());
            label.getStyleClass().add("standard-text");
            hBox.setPadding(new Insets(2));
            hBox.setSpacing(10);
            hBox.setAlignment(Pos.CENTER_LEFT);
            hBox.getChildren().addAll(circle, label);
            areasBox.getChildren().add(hBox);
        }
    }

    public Node getRoot() {
        return root;
    }

    @FXML
    public void onAddNewParticipant() {
        if (overlayController == null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/mentis/overlays/new-participant-overlay.fxml"));
                Parent overlayRoot = loader.load();
                overlayController = loader.getController();
                overlayController.showProperty().addListener((observable, oldValue, newValue) -> {
                    overlayController.getRoot().setVisible(newValue);
                    projectPane.setEffect(newValue ? new GaussianBlur(20) : null);
                });
                root.getChildren().add(overlayRoot);
                projectPane.setEffect(new GaussianBlur(20));
            } catch (IOException e) {
                log.error("Something went wrong while loading new participant overlay: " + e.getMessage());
            }
        } else {
            overlayController.refresh();
        }
        overlayController.showProperty().set(true);
    }

    @FXML
    public void onEdit() {
        ViewManager.getInstance().changeView(View.PROJECT_SETTINGS);
    }

    @FXML
    public void onBack() {
        ViewManager.getInstance().changeView(View.MAIN_MENU);
    }

    @FXML
    public void onDownload() {
        Thread thread = new Thread(() -> {
            Platform.runLater(() -> downloadButton.getStyleClass().add("pending"));
            ExcelCreator.createExcel(Manager.getInstance().getCurrentProject());
            Platform.runLater(() -> downloadButton.getStyleClass().remove(downloadButton.getStyleClass().size() - 1));
            Platform.runLater(() -> downloadButton.getStyleClass().add("successful-upload"));
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            Platform.runLater(() -> downloadButton.getStyleClass().remove(downloadButton.getStyleClass().size() - 1));
        });
        thread.start();
    }

}
