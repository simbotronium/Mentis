package com.example.mentis.presentation;

import com.example.mentis.business.data.Voxel;
import com.example.mentis.business.logic.Manager;
import com.example.mentis.business.logic.ValidationStatus;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Polygon;

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
    private Voxel selectedVoxel;


    @FXML
    public void initialize() {
        System.out.println("searching overlay here: " + manager.getCurrentExamination().getOverlayFile().toURI());
        Image overlay = new Image(manager.getCurrentExamination().getOverlayFile().toURI().toString());
        selectionBox.setVisible(false);

        leftImage.fitWidthProperty().bind(leftStack.widthProperty());
        leftImage.fitHeightProperty().bind(leftStack.heightProperty());
        rightImage.fitWidthProperty().bind(rightStack.widthProperty());
        rightImage.fitHeightProperty().bind(rightStack.heightProperty());
        middleImage.fitWidthProperty().bind(middleStack.widthProperty());
        middleImage.fitHeightProperty().bind(middleStack.heightProperty());

        leftImage.setImage(overlay);
        middleImage.setImage(overlay);
        rightImage.setImage(overlay);

        createVoxelViews();
    }

    private void createVoxelViews() {
        // Property to trigger polygon rendering
        SimpleBooleanProperty renderProperty = new SimpleBooleanProperty(false);

        for (Voxel voxel: manager.getCurrentExamination().getVoxels()) {
            StackPane cell = createContainer(voxel);
            Polygon triangle = new VoxelComponent(cell, voxel.getValidationStatus().getColor().brighter(), voxel.getValidationStatus().getColor(), 0.5, renderProperty, voxel);
            cell.getChildren().add(triangle);
            GridPane.setHgrow(cell, Priority.ALWAYS);
            GridPane.setVgrow(cell, Priority.ALWAYS);

            leftGrid.add(cell, voxel.getRow(), voxel.getCol());
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
        return cell;
    }

    private void setSelectedVoxel(Voxel v) {
        if (v.isSelected()) {
            selectionLabel.setText("selected voxel: " + v.toString());
            this.selectionBox.setVisible(true);
            this.selectedVoxel = v;
        } else {
            selectionLabel.setText("no voxel selected");
            this.selectionBox.setVisible(false);
            this.selectedVoxel = null;
        }
    }

    public void onValidate() {
        this.setSelectedVoxelValidationStatus(true);
    }

    public void onRefute() {
        this.setSelectedVoxelValidationStatus(false);
    }

    private void setSelectedVoxelValidationStatus(boolean valid) {
        this.selectedVoxel.setValidationStatus(valid ? ValidationStatus.VALID : ValidationStatus.REFUTED);
        this.selectedVoxel.selectedProperty().set(false);
        this.selectionBox.setVisible(false);
        this.selectedVoxel = null;
    }

    @Override
    public VBox getRoot() {
        return root;
    }
}
