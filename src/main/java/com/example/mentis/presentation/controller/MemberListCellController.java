package com.example.mentis.presentation.controller;

import com.example.mentis.business.data.Examination;
import com.example.mentis.business.data.Member;
import com.example.mentis.business.logic.Manager;
import com.example.mentis.business.logic.View;
import com.example.mentis.presentation.ViewManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.List;
import java.util.stream.Collectors;

public class MemberListCellController implements Controller {

    @FXML
    private GridPane root;

    @FXML
    private Label idLabel;

    @FXML
    private Label examinationsLabel;

    private Member member;
    private final Manager manager = Manager.getInstance();

    public void setMember(Member m) {
        member = m;
        idLabel.setText(Long.toString(m.getId()));
        examinationsLabel.setText(createExaminationsString(m.getExaminations()));
    }

    @FXML
    protected void onEdit() {
        if (member != null) {
            manager.currentMemberProperty().set(member);
            ViewManager.getInstance().changeView(View.MEMBER);
        }
    }

    private String createExaminationsString(List<Examination> examinations) {
        return examinations.stream()
                .map(examination -> examination.getExam() + Integer.toString(examination.getSlice()))
                .collect(Collectors.joining(","));
    }

    @Override
    public GridPane getRoot() {
        return root;
    }
}
