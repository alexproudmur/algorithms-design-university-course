module com.example.lab6 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    opens com.example.lab6 to javafx.fxml;
    exports com.example.lab6;
    exports com.example.lab6.controller;
    opens com.example.lab6.controller to javafx.fxml;
}