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
        loadScene("login");
    }

    @FXML
    public void registerClicked(ActionEvent actionEvent) {
        loadScene("register");
    }

    private void loadScene(String fileName){
        try {
            Scene loginScene = new Scene(App.loadFXML(fileName));
            App.getInstance().getStage().setScene(loginScene);
        } catch (IOException e){
            logger.info("The login fxml file couldn't be loaded");
            e.printStackTrace();
        }
    }

}
