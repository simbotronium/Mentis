package com.example.mentis.presentation.components;

import com.example.mentis.business.data.Examination;
import com.example.mentis.presentation.ViewManager;
import com.example.mentis.presentation.controller.ExamListCellController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ExamListCell extends ListCell<Examination> {

    private Parent root;
    private ExamListCellController controller;
    private final Logger log = LoggerFactory.getLogger(ExamListCell.class);

    public ExamListCell() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/mentis/Components/ExamListElement.fxml"));
            root = loader.load();
            controller = loader.getController();
        } catch (IOException e) {
            log.error("Something went wrong while loading FXML: " + e.getMessage());
        }
    }

    @Override
    protected void updateItem(Examination exam, boolean empty) {
        super.updateItem(exam, empty);

        if (empty || exam == null) {
            setGraphic(null);
            setText(null);
        } else {
            setText(null);
            setGraphic(root);
            controller.setExamination(exam);
        }
    }

}
