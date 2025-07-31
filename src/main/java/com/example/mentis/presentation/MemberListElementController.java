package com.example.mentis.presentation;

import com.example.mentis.business.data.Examination;
import com.example.mentis.business.data.Member;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.List;
import java.util.stream.Collectors;

public class MemberListElementController implements Controller {

    @FXML
    private GridPane root;

    @FXML
    private Label idLabel;

    @FXML
    private Label examinationsLabel;

    public void setMember(Member m) {
        System.out.println("setting member with id: " +  m.getId());
        idLabel.setText(Long.toString(m.getId()));
        examinationsLabel.setText(createExaminationsString(m.getExaminations()));
    }

    private String createExaminationsString(List<Examination> examinations) {
        return examinations.stream()
                .map(examination -> examination.getLayer() + Integer.toString(examination.getNum()))
                .collect(Collectors.joining(","));
    }

    @Override
    public GridPane getRoot() {
        return root;
    }
}
