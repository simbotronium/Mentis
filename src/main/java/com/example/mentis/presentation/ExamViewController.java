package com.example.mentis.presentation;

import com.example.mentis.business.data.Voxel;
import com.example.mentis.business.logic.Manager;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

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
    public void initialize() {
        System.out.println("searching overlay here: " + manager.getCurrentExamination().getOverlayFile().toURI());
        Image overlay = new Image(manager.getCurrentExamination().getOverlayFile().toURI().toString());

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
            StackPane cell = new StackPane();
            cell.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            Polygon triangle = new Triangle(cell, voxel.getValidationStatus().getColor().brighter(), voxel.getValidationStatus().getColor(), 0.5, renderProperty);
            cell.getChildren().add(triangle);
            GridPane.setHgrow(cell, Priority.ALWAYS);
            GridPane.setVgrow(cell, Priority.ALWAYS);

            leftGrid.add(cell, voxel.getRow(), voxel.getCol());
        }

        Platform.runLater(() -> renderProperty.set(true));
    }

    @Override
    public VBox getRoot() {
        return root;
    }
}
