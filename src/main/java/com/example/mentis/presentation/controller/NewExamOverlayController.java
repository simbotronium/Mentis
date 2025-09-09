package com.example.mentis.presentation.controller;

import com.example.mentis.business.data.Examination;
import com.example.mentis.business.logic.Manager;
import com.example.mentis.presentation.ViewManager;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.IntegerStringConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class NewExamOverlayController implements Controller {

    @FXML
    private VBox root;
    @FXML
    private TextField examTextField;
    @FXML
    private TextField sliceTextField;
    @FXML
    private Button overlayUploadButton;
    @FXML
    private Button txtUploadButton;

    private Examination examination = new Examination();
    private final Manager manager = Manager.getInstance();
    private final SimpleBooleanProperty show = new SimpleBooleanProperty(true);
    private final Logger log = LoggerFactory.getLogger(NewExamOverlayController.class);

    @FXML
    public void initialize() {
        refresh();
    }

    public void refresh() {
        examination = new Examination();
        examTextField.setText("");
        sliceTextField.setText("");
        examTextField.getStyleClass().remove("input-error");
        sliceTextField.getStyleClass().remove("input-error");
        resetUpButtonStyles();
    }

    private void resetUpButtonStyles() {
        overlayUploadButton.getStyleClass().clear();
        overlayUploadButton.getStyleClass().add("new-element-button");
        txtUploadButton.getStyleClass().clear();
        txtUploadButton.getStyleClass().add("new-element-button");

        overlayUploadButton.setWrapText(true);
        overlayUploadButton.setTextAlignment(TextAlignment.CENTER);
        overlayUploadButton.setAlignment(Pos.CENTER);
        txtUploadButton.setWrapText(true);
        txtUploadButton.setTextAlignment(TextAlignment.CENTER);
        txtUploadButton.setAlignment(Pos.CENTER);

        overlayUploadButton.setText("+\nOverlay");
        txtUploadButton.setText("+\ntxt File");
    }

    @FXML
    public void onCancel() {
        refresh();
        show.set(false);
    }

    @FXML
    public void onOk() {
        if (validInput()) {
            manager.addExamination(examination);
            refresh();
            show.set(false);
        }
    }

    private boolean validInput() {
        boolean valid = true;
        if (!examTextField.getText().isEmpty() && examTextField.getText().matches("[A-Za-z]+")) {
            examination.setExam(examTextField.getText());
        } else {
            examTextField.setText("");
            examTextField.getStyleClass().add("input-error");
            valid = false;
        }
        try {
            examination.setSlice(Integer.parseInt(sliceTextField.getText()));
        } catch (NumberFormatException e) {
            sliceTextField.setText("");
            sliceTextField.getStyleClass().add("unsuccessful-upload");
            valid = false;
        }

        if (examination.getOverlayFile() == null) {
            overlayUploadButton.getStyleClass().add("unsuccessful-upload");
            valid = false;
        }
        if (examination.getTxtFilePath().isEmpty()) {
            txtUploadButton.getStyleClass().add("unsuccessful-upload");
            valid = false;
        }

        return valid;
    }

    @FXML
    public void ontxtUpload() {
        handleFileUpload(txtUploadButton, "*.txt", "Textfiles");
    }

    @FXML
    public void onOverlayUpload() {
        handleFileUpload(overlayUploadButton, "*.jpeg", "Picturefiles");
    }

    private void handleFileUpload(Button sourceButton, String fileExtension, String fileDescription) {
        Stage stage = (Stage) root.getScene().getWindow();

        if (stage == null) {
            log.error("No Stage set in NewExamOverlayController");
            return;
        }
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("select file");

        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter(fileDescription, fileExtension)
        );

        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            log.info("selected File: " + selectedFile.getPath());
            if (fileDescription.equals("Textfiles")) {
                examination.setTxtFilePath(selectedFile.getAbsolutePath());
            } else {
                examination.setOverlayFile(selectedFile);
            }
            sourceButton.setText(selectedFile.getName());
            sourceButton.getStyleClass().remove("unsuccessful-upload");
            sourceButton.getStyleClass().add("successful-upload");

        } else {
            log.info("no file selected");
            sourceButton.getStyleClass().add("unsuccessful-upload");
        }
    }

    @Override
    public VBox getRoot() {
        return root;
    }

    public SimpleBooleanProperty showProperty() {
        return show;
    }

}
