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

    private final TextFormatter<Long> idFormatter = new TextFormatter<>(new LongStringConverter());
    private final TextFormatter<Integer> ageFormatter = new TextFormatter<>(new IntegerStringConverter());
    private final SimpleBooleanProperty show = new SimpleBooleanProperty(true);

    @FXML
    public void initialize() {
        idFormatter.valueProperty().set(0L);
        ageFormatter.valueProperty().set(0);

        idTextField.setTextFormatter(idFormatter);
        ageTextField.setTextFormatter(ageFormatter);

        refresh();
    }

    public void refresh() {
        member = new Member();
        idFormatter.valueProperty().set(0L);
        ageFormatter.valueProperty().set(0);
        member.idProperty().asObject().bindBidirectional(idFormatter.valueProperty());
        member.ageProperty().asObject().bindBidirectional(ageFormatter.valueProperty());
    }

    public void onRadioButtonPress(ActionEvent e) {
        RadioButton source = (RadioButton) e.getSource();
        this.member.sideProperty().set(Side.fromRadioButtonId(source.getId()));
    }

    public void onOk() {
        manager.addMember(member);
        refresh();
        show.set(false);
    }

    @Override
    public VBox getRoot() {
        return root;
    }

    public SimpleBooleanProperty showProperty() {
        return show;
    }
}
