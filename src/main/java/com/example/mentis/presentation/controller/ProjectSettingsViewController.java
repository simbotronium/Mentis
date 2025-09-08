package com.example.mentis.presentation.controller;

import com.example.mentis.business.data.Area;
import com.example.mentis.business.data.Project;
import com.example.mentis.business.logic.Manager;
import com.example.mentis.business.logic.UID;
import com.example.mentis.business.logic.View;
import com.example.mentis.presentation.ViewManager;
import com.example.mentis.presentation.components.AreaListCell;
import com.example.mentis.presentation.components.InfoComponent;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import javafx.util.converter.IntegerStringConverter;

import java.util.ArrayList;
import java.util.List;

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
    @FXML
    private VBox infoBox;
    @FXML
    private Button newInfoButton;
    private FadeTransition showTransition;
    private FadeTransition hideTransition;

    private final TextFormatter<Integer> voxelNumberFormatter = new TextFormatter<>(new IntegerStringConverter());
    private final TextFormatter<Integer> deviationFormatter = new TextFormatter<>(new IntegerStringConverter());

    public Node getRoot() {
        return root;
    }

    @FXML
    public void initialize() {
        HBox.setHgrow(infoBox, Priority.ALWAYS);

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
        createInfos(project.getInfos());
    }

    private void createInfos(List<String> infos) {
        infoBox.getChildren().clear();
        for (int i = 0; i < infos.size(); i++) {
            InfoComponent infoComponent = new InfoComponent(i);
            setUpInfoComponentListeners(infoComponent);
            infoComponent.getTextField().setText(infos.get(i));
            infoComponent.getTextField().setVisible(false);
            infoComponent.getLabel().setVisible(true);
            infoComponent.getLabel().setText(infos.get(i));
            infoBox.getChildren().add(infoComponent);
        }
    }

    private void setUpInfoComponentListeners(InfoComponent ic) {
        ic.getTextField().setOnAction(e -> {
            Manager.getInstance().getCurrentProject().getInfos().set(ic.getIndex(), ic.getTextField().getText());
            ic.getLabel().setText(ic.getTextField().getText());
            ic.getLabel().setVisible(true);
            ic.getTextField().setVisible(false);
        });
        ic.setOnMouseClicked(e -> {
            if (ic.getTextField().isVisible()) {
                return;
            }
            ic.getLabel().setVisible(false);
            ic.getTextField().setVisible(true);
        });
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
    public void onNewInfo() {
        InfoComponent infoComponent = new InfoComponent(Manager.getInstance().getCurrentProject().getInfos().size());
        infoBox.getChildren().add(infoComponent);
        Manager.getInstance().getCurrentProject().addInfo("");
        setUpInfoComponentListeners(infoComponent);
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
