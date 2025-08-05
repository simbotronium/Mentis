package com.example.mentis.presentation;

import com.example.mentis.business.data.Member;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;

import java.io.IOException;

public class MemberListCell extends ListCell<Member> {

    private Parent root;
    private MemberListElementController controller;

    public MemberListCell() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/mentis/Components/MemberListElement.fxml"));
            root = loader.load();
            controller = loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
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
