package com.example.mentis.presentation;

import com.example.mentis.business.data.Examination;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;

import java.io.IOException;

public class ExamListCell extends ListCell<Examination> {

    private Parent root;
    private ExamListCellController controller;

    public ExamListCell() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/mentis/Components/ExamListElement.fxml"));
            root = loader.load();
            controller = loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void updateItem(Examination exam, boolean empty) {
        super.updateItem(exam, empty);

        if (empty || exam == null) {
            setGraphic(null);
            setText(null);
        } else {
            controller.setExamination(exam);
            setGraphic(root);
        }
    }

}
