package ro.cristian.accesaquest.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ro.cristian.accesaquest.App;
import ro.cristian.accesaquest.database.PlayerDB;
import ro.cristian.accesaquest.util.Notification;

import java.util.logging.Logger;

public class Login {
    private static final Logger logger = Logger.getLogger("| Login | ");
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;

    @FXML
    private void login(ActionEvent actionEvent) {
        logger.info("Checking login credentials");

        PlayerDB playerDB = new PlayerDB();
        var res = playerDB.login(emailField.getText(), passwordField.getText());

        if(!res) {
            Notification.showErrorNotification("Incorrect credentials");
            emailField.clear();
            passwordField.clear();
        }
        else{
            Notification.showConfirmationNotification("Login Confirmation", "Logged in successfully");
            App.getInstance().loadScene("profile");
        }
    }
}
