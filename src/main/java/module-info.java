module ro.cristian.accesaquest {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;

    opens ro.cristian.accesaquest to javafx.fxml;
    exports ro.cristian.accesaquest;
}