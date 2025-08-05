package com.example.mentis.presentation;

import com.example.mentis.business.data.Examination;
import com.example.mentis.business.data.Member;
import com.example.mentis.business.logic.Manager;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;

public class MemberViewController implements Controller {

    private final Manager manager = Manager.getInstance();

    @FXML
    private StackPane root;
    @FXML
    private ListView<Examination> examListView;
    @FXML
    private Label examinationsLabel;
    @FXML
    private Label titleLabel;

    @FXML
    public void initialize() {
        manager.currentMemberProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("changed Member");
            if (newValue != null) {
                refreshView(newValue);
            }
        });

        examListView.visibleProperty().bind(Bindings.isNotEmpty(manager.getCurrentMember().getExaminations()));
        examListView.managedProperty().bind(examListView.visibleProperty());

        examListView.setItems(manager.getCurrentMember().getExaminations());
        examListView.setCellFactory(list -> new ExamListCell());

        if (manager.getCurrentMember() != null) {
            refreshView(manager.getCurrentMember());
        }
    }

    private void refreshView(Member member) {
        examinationsLabel.setText("Examinations: " + member.getExaminations().size());
        titleLabel.setText("Member: " + member.getId());
    }

    @Override
    public StackPane getRoot() {
        return root;
    }
}
