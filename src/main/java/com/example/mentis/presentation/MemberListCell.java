package com.example.mentis.presentation;

import com.example.mentis.business.data.Member;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;

import java.io.IOException;

public class MemberListCell extends ListCell<Member> {

    @Override
    protected void updateItem(Member member, boolean empty) {
        super.updateItem(member, empty);

        if (empty || member == null) {
            setGraphic(null);
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/mentis/Components/MemberListElement.fxml"));
                Parent root = loader.load();
                MemberListElementController controller = loader.getController();
                controller.setMember(member);

                setGraphic(root);
            } catch (IOException e) {
                e.printStackTrace();
                setGraphic(null);
            }
        }
    }

}
