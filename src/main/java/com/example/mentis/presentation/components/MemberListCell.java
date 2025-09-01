package com.example.mentis.presentation.components;

import com.example.mentis.business.data.Member;
import com.example.mentis.presentation.ViewManager;
import com.example.mentis.presentation.controller.MemberListCellController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class MemberListCell extends ListCell<Member> {

    private Parent root;
    private MemberListCellController controller;
    private final Logger log = LoggerFactory.getLogger(MemberListCell.class);

    public MemberListCell() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/mentis/Components/MemberListElement.fxml"));
            root = loader.load();
            controller = loader.getController();
        } catch (IOException e) {
            log.error("Something went wrong while loading FXML: " + e.getMessage());
        }
    }

    @Override
    protected void updateItem(Member member, boolean empty) {
        super.updateItem(member, empty);

        if (empty || member == null) {
            setGraphic(null);
            setText(null);
        } else {
            setGraphic(root);
            setText(null);
            controller.setMember(member);
        }
    }

}
