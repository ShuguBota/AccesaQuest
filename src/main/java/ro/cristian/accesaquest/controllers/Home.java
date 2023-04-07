package ro.cristian.accesaquest.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import ro.cristian.accesaquest.App;

import java.net.URL;
import java.util.ResourceBundle;

public class Home implements Initializable {

    public void createQuest(ActionEvent actionEvent) {
        App.getInstance().openWindow("Create Quest", "create_quest", 0.7);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
