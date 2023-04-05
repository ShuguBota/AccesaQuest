module ro.cristian.accesaquest {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.junit.jupiter.api;
    requires com.azure.cosmos;
    requires json.simple;
    requires java.sql;

    opens ro.cristian.accesaquest.controllers to javafx.fxml;
    exports ro.cristian.accesaquest;
}