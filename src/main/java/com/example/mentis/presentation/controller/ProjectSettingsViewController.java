package com.example.mentis.presentation.controller;

import com.example.mentis.business.data.Area;
import com.example.mentis.business.data.Project;
import com.example.mentis.business.logic.Manager;
import com.example.mentis.business.logic.UID;
import com.example.mentis.business.logic.View;
import com.example.mentis.presentation.ViewManager;
import com.example.mentis.presentation.components.AreaListCell;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import javafx.util.converter.IntegerStringConverter;

public class ProjectSettingsViewController implements Controller {

    @FXML
    private Node root;
    @FXML
    private VBox projectPane;
    @FXML
    private VBox newAreaOverlay;
    @FXML
    private TextField areaNameField;
    @FXML
    private ColorPicker colorPicker;
    @FXML
    private ListView<Area> areaList;
    @FXML
    private TextField projectNameField;
    @FXML
    private TextField spectroscopyField;
    @FXML
    private TextField voxelNumberField;
    @FXML
    private TextField voxelOrderField;
    @FXML
    private TextField deviationField;
    private FadeTransition showTransition;
    private FadeTransition hideTransition;

    private final TextFormatter<Integer> voxelNumberFormatter = new TextFormatter<>(new IntegerStringConverter());
    private final TextFormatter<Integer> deviationFormatter = new TextFormatter<>(new IntegerStringConverter());

    public Node getRoot() {
        return root;
    }

    @FXML
    public void initialize() {
        showTransition = new FadeTransition(Duration.millis(200), newAreaOverlay);
        showTransition.setFromValue(0.0);
        showTransition.setToValue(1.0);

        hideTransition = new FadeTransition(Duration.millis(200), newAreaOverlay);
        hideTransition.setFromValue(1.0);
        hideTransition.setToValue(0.0);
        hideTransition.setOnFinished(e -> newAreaOverlay.setVisible(false));

        newAreaOverlay.setVisible(false);

        projectNameField.setOnMouseClicked(e -> projectNameField.selectAll());
        voxelNumberFormatter.setValue(0);
        deviationFormatter.setValue(0);
        voxelNumberField.setTextFormatter(voxelNumberFormatter);
        deviationField.setTextFormatter(deviationFormatter);
        updateView(Manager.getInstance().getCurrentProject());

        Manager.getInstance().currentProjectProperty().addListener((observable, oldValue, newValue) -> {
            oldValue.voxelDimensionSizeProperty().asObject().unbindBidirectional(voxelNumberFormatter.valueProperty());
            oldValue.maxDeviationProperty().asObject().unbindBidirectional(deviationFormatter.valueProperty());
            oldValue.typeOfSpectroscopyProperty().unbindBidirectional(spectroscopyField.textProperty());
            oldValue.nameProperty().unbindBidirectional(projectNameField.textProperty());
            updateView(newValue);
        });
        areaList.setCellFactory(list -> new AreaListCell(area -> {
            Manager.getInstance().getCurrentProject().getAreas().remove(area);
        }));
    }

    private void updateView(Project project) {
        projectNameField.setText(project.getName());
        voxelNumberFormatter.setValue(project.getVoxelDimensionSize());
        deviationFormatter.setValue(project.getMaxDeviation());
        spectroscopyField.setText(project.getTypeOfSpectroscopy());

        project.voxelDimensionSizeProperty().asObject().bindBidirectional(voxelNumberFormatter.valueProperty());
        project.maxDeviationProperty().asObject().bindBidirectional(deviationFormatter.valueProperty());
        project.typeOfSpectroscopyProperty().bindBidirectional(spectroscopyField.textProperty());
        project.nameProperty().bindBidirectional(projectNameField.textProperty());

        areaList.setItems(project.getAreas());
    }

    public void onOverlayOk() {
        if (areaNameField.getText().matches("^[A-Za-z]+$")) {
            Manager.getInstance().getCurrentProject().addArea(new Area(UID.next(), colorPicker.getValue(), areaNameField.getText()));
            closeOverlay();
        } else {
            areaNameField.getStyleClass().add("input-error");
        }
    }

    public void onNewArea() {
        projectPane.setEffect(new GaussianBlur(20));
        newAreaOverlay.setVisible(true);
        showTransition.play();
    }

    public void closeOverlay() {
        projectPane.setEffect(null);
        hideTransition.play();
    }

    @FXML
    public void onBack() {
        ViewManager.getInstance().changeView(View.MAIN_MENU);
    }

    @FXML
    public void onConfirm() {
        Manager.getInstance().confirmProject();
    }

}
