package com.example.mentis.presentation.components;

import com.example.mentis.business.data.Participant;
import com.example.mentis.presentation.controller.ParticipantListCellController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ParticipantListCell extends ListCell<Participant> {

    private Parent root;
    private ParticipantListCellController controller;
    private final Logger log = LoggerFactory.getLogger(ParticipantListCell.class);

    public ParticipantListCell() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/mentis/Components/ParticipantListElement.fxml"));
            root = loader.load();
            controller = loader.getController();
        } catch (IOException e) {
            log.error("Something went wrong while loading FXML: " + e.getMessage());
        }
    }

    @Override
    protected void updateItem(Participant participant, boolean empty) {
        super.updateItem(participant, empty);

        if (empty || participant == null) {
            setGraphic(null);
            setText(null);
        } else {
            setGraphic(root);
            setText(null);
            controller.setParticipant(participant);
        }
    }

}
