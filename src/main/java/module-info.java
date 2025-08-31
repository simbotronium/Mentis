module com.example.mentis {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;

    opens com.example.mentis.application to javafx.fxml;
    opens com.example.mentis.presentation to javafx.fxml;
    opens com.example.mentis.business.data to com.fasterxml.jackson.databind;
    opens com.example.mentis.business.logic to com.fasterxml.jackson.databind;
    exports com.example.mentis.application;
    exports com.example.mentis.business.data;
    exports com.example.mentis.application.serialisation;
    exports com.example.mentis.business.logic;
    opens com.example.mentis.application.serialisation to javafx.fxml;
}
