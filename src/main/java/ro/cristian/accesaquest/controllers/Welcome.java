package ro.cristian.accesaquest.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;

import ro.cristian.accesaquest.App;

import java.io.IOException;
import java.util.logging.Logger;

public class Welcome {
    private static final Logger logger = Logger.getLogger("| Welcome | ");

    @FXML
    public void loginClicked(ActionEvent actionEvent) {
        App.getInstance().loadScene("login");
    }

    @FXML
    public void registerClicked(ActionEvent actionEvent) {
        App.getInstance().loadScene("register");
    }

}
