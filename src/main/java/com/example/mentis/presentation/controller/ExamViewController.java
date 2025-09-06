package com.example.mentis.presentation.controller;

import com.example.mentis.business.data.Area;
import com.example.mentis.business.data.Voxel;
import com.example.mentis.business.logic.Manager;
import com.example.mentis.business.logic.ValidationStatus;
import com.example.mentis.business.logic.View;
import com.example.mentis.presentation.ViewManager;
import com.example.mentis.presentation.components.VoxelComponent;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Polygon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class ExamViewController implements Controller {

    private final Manager manager = Manager.getInstance();

    @FXML
    private VBox root;

    @FXML
    private ImageView leftImage;
    @FXML
    private ImageView middleImage;
    @FXML
    private ImageView rightImage;
    @FXML
    private GridPane leftGrid;
    @FXML
    private GridPane middleGrid;
    @FXML
    private GridPane rightGrid;
    @FXML
    private StackPane leftStack;
    @FXML
    private StackPane middleStack;
    @FXML
    private StackPane rightStack;
    @FXML
    private VBox selectionBox;
    @FXML
    private Label selectionLabel;
    @FXML
    private ChoiceBox<Area> areaChoice;
    @FXML
    private VBox mappingBox;
    private Voxel selectedVoxel;
    private final Logger log = LoggerFactory.getLogger(ExamViewController.class);


    //TODO: fix voxel selection issue
    @FXML
    public void initialize() {
        log.info("searching overlay here: " + manager.getCurrentExamination().getOverlayFile().toURI());
        Image overlay = new Image(manager.getCurrentExamination().getOverlayFile().toURI().toString());

        ObservableList<Area> areas = Manager.getInstance().getCurrentProject().getAreas();
        areaChoice.setItems(areas);
        if (!areas.isEmpty()) areaChoice.setValue(areas.get(0));
        areaChoice.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedVoxel.setArea(newValue);
            this.setSelectedVoxel(null);
        });

        leftImage.fitWidthProperty().bind(leftStack.widthProperty());
        leftImage.fitHeightProperty().bind(leftStack.heightProperty());
        rightImage.fitWidthProperty().bind(rightStack.widthProperty());
        rightImage.fitHeightProperty().bind(rightStack.heightProperty());
        middleImage.fitWidthProperty().bind(middleStack.widthProperty());
        middleImage.fitHeightProperty().bind(middleStack.heightProperty());

        leftImage.setImage(overlay);
        middleImage.setImage(overlay);
        rightImage.setImage(overlay);

        Arrays.stream(manager.getCurrentExamination().getVoxels()).forEach(v -> v.selectedProperty().set(false));
        this.setSelectedVoxel(null);
        createVoxelViews();
    }

    private void createVoxelViews() {
        // Property to trigger polygon rendering
        SimpleBooleanProperty renderProperty = new SimpleBooleanProperty(false);

        for (Voxel voxel: manager.getCurrentExamination().getVoxels()) {
            StackPane cell0 = createContainer(voxel);
            Polygon triangle0 = new VoxelComponent(cell0, 0, 0.5, renderProperty, voxel);
            cell0.getChildren().add(triangle0);

            StackPane cell1 = createContainer(voxel);
            Polygon triangle1 = new VoxelComponent(cell1, 1, 0.5, renderProperty, voxel);
            cell1.getChildren().add(triangle1);

            StackPane cell2 = createContainer(voxel);
            Polygon triangle2 = new VoxelComponent(cell2, 2, 0.5, renderProperty, voxel);
            cell2.getChildren().add(triangle2);

            leftGrid.add(cell0, voxel.getRow(), manager.getCurrentExamination().getVoxelDimensionSize() - voxel.getCol());
            middleGrid.add(cell1, voxel.getRow(), manager.getCurrentExamination().getVoxelDimensionSize() - voxel.getCol());
            rightGrid.add(cell2, voxel.getRow(), manager.getCurrentExamination().getVoxelDimensionSize() - voxel.getCol());
        }

        Platform.runLater(() -> renderProperty.set(true));
    }

    private StackPane createContainer(Voxel voxel) {
        StackPane cell = new StackPane();
        cell.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        cell.setOnMouseClicked(e -> {
            if (voxel.isSelected()) {
                voxel.selectedProperty().set(false);
            } else {
                Arrays.stream(manager.getCurrentExamination().getVoxels()).forEach(v -> v.selectedProperty().set(false));
                voxel.selectedProperty().set(true);
            }
            this.setSelectedVoxel(voxel);
        });
        GridPane.setHgrow(cell, Priority.ALWAYS);
        GridPane.setVgrow(cell, Priority.ALWAYS);
        return cell;
    }

    private void setSelectedVoxel(Voxel v) {
        if (v == null) {
            this.selectionBox.setVisible(false);
            this.mappingBox.setVisible(false);
            this.selectedVoxel = null;
            return;
        }
        if (v.isSelected()) {
            this.selectedVoxel = v;
            selectionLabel.setText("selected voxel: " + v);
            this.selectionBox.setVisible(true);
            this.mappingBox.setVisible(true);
            this.areaChoice.setValue(v.getArea());
        } else {
            selectionLabel.setText("no voxel selected");
            this.selectionBox.setVisible(false);
            this.mappingBox.setVisible(false);
            this.selectedVoxel = null;
            areaChoice.setValue(null);
        }
    }

    @FXML
    public void onValidate() {
        this.setSelectedVoxelValidationStatus(true);
    }

    @FXML
    public void onRefute() {
        this.setSelectedVoxelValidationStatus(false);
    }

    private void setSelectedVoxelValidationStatus(boolean valid) {
        this.selectedVoxel.setValidationStatus(valid ? ValidationStatus.VALID : ValidationStatus.REFUTED);
        this.selectionBox.setVisible(false);
        this.selectedVoxel = null;
    }

    @FXML
    public void onSave() {
        ViewManager.getInstance().changeView(View.PARTICIPANT);
    }

    @Override
    public VBox getRoot() {
        return root;
    }
}
