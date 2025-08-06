package com.example.mentis.presentation;

import com.example.mentis.business.data.Examination;
import com.example.mentis.business.logic.Manager;
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

    private final TextFormatter<String> examFormatter = new TextFormatter<>(new DefaultStringConverter());
    private final TextFormatter<Integer> sliceFormatter = new TextFormatter<>(new IntegerStringConverter());
    private final SimpleBooleanProperty show = new SimpleBooleanProperty(true);

    @FXML
    public void initialize() {
        sliceFormatter.valueProperty().set(0);

        examTextField.setTextFormatter(examFormatter);
        sliceTextField.setTextFormatter(sliceFormatter);

        refresh();
    }

    public void refresh() {
        examination = new Examination();
        sliceFormatter.valueProperty().set(0);
        examination.examProperty().bindBidirectional(examFormatter.valueProperty());
        examination.sliceProperty().asObject().bindBidirectional(sliceFormatter.valueProperty());
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
    }

    @FXML
    public void onOk() {
        manager.addExamination(examination);
        refresh();
        show.set(false);
    }

    @FXML
    public void ontxtUpload() {
        handleFileUpload(overlayUploadButton, "*.txt", "Textdateien");
    }

    private void handleFileUpload(Button button, String fileExtension, String fileDescription) {
        Stage stage = (Stage) root.getScene().getWindow();

        if (stage == null) {
            System.out.println("No Stage set in NewExamOverlayController");
            return;
        }
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("select file");

        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter(fileDescription, fileExtension)
        );

        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            System.out.println("selected File: " + selectedFile.getAbsolutePath());
            button.setText(selectedFile.getName());
            button.getStyleClass().add("successful-upload");

        } else {
            System.out.println("no file selected");
            button.getStyleClass().add("unsuccessful-upload");
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
