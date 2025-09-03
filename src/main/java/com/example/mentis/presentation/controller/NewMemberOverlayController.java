package com.example.mentis.presentation.controller;

import com.example.mentis.business.data.Member;
import com.example.mentis.business.logic.Manager;
import com.example.mentis.business.logic.Side;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.VBox;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.LongStringConverter;

public class NewMemberOverlayController implements Controller {

    @FXML
    private VBox root;

    @FXML
    private TextField idTextField;

    @FXML
    private TextField ageTextField;

    private Member member = new Member();
    private final Manager manager = Manager.getInstance();
    private final SimpleBooleanProperty show = new SimpleBooleanProperty(true);

    @FXML
    public void initialize() {
        refresh();
    }

    public void refresh() {
        member = new Member();
    }

    public void onRadioButtonPress(ActionEvent e) {
        RadioButton source = (RadioButton) e.getSource();
        this.member.sideProperty().set(Side.fromRadioButtonId(source.getId()));
    }

    public void onOk() {
        if (validInput()) {
            manager.getCurrentProject().addMember(member);
            refresh();
            show.set(false);
        }
    }

    private boolean validInput() {
        boolean valid = true;
        try {
            member.setAge(Integer.parseInt(ageTextField.getText()));
        } catch (NumberFormatException e) {
            ageTextField.setText("");
            ageTextField.getStyleClass().add("input-error");
            valid = false;
        }
        try {
            member.setId(Long.parseLong(idTextField.getText()));
        } catch (NumberFormatException e) {
            idTextField.setText("");
            idTextField.getStyleClass().add("input-error");
            valid = false;
        }

        return valid;
    }

    @Override
    public VBox getRoot() {
        return root;
    }

    public SimpleBooleanProperty showProperty() {
        return show;
    }
}
