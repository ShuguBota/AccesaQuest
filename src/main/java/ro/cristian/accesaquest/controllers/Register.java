package ro.cristian.accesaquest.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ro.cristian.accesaquest.models.Player;
import ro.cristian.accesaquest.database.PlayerDB;
import ro.cristian.accesaquest.util.Notification;

import java.util.logging.Logger;

public class Register {
    private static final Logger logger = Logger.getLogger("| Register | ");
    @FXML private TextField emailField;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    @FXML
    private void register(ActionEvent actionEvent) {
        Player player = new Player(usernameField.getText(), emailField.getText(), passwordField.getText());

        PlayerDB playerDB = new PlayerDB();
        boolean res = false;

        try {
            res = playerDB.createPlayer(player);
        } catch (Exception e) {
            Notification.showErrorNotification(e);
            return;
        }

        if(!res) {
            Notification.showErrorNotification("Email already in use");
            emailField.clear();
            usernameField.clear();
            passwordField.clear();
        }
        else{
            Notification.showConfirmationNotification("Register confirmation", "You have registered successfully");
        }
    }
}
