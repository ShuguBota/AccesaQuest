module ro.cristian.accesaquest {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.junit.jupiter.api;
    requires com.azure.cosmos;
    requires json.simple;
    requires java.logging;
    //requires cassandra.all;

    opens ro.cristian.accesaquest.controllers;
    exports ro.cristian.accesaquest;
}