module com.example.mentis {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.mentis.application to javafx.fxml;
    opens com.example.mentis.presentation to javafx.fxml;
    exports com.example.mentis.application;
}
