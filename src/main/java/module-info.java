module ro.cristian.accesaquest {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires org.junit.jupiter.api;


    opens ro.cristian.accesaquest to javafx.fxml;
    exports ro.cristian.accesaquest;
}