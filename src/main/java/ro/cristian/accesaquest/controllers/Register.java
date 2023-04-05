package ro.cristian.accesaquest.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ro.cristian.accesaquest.models.Player;
import ro.cristian.accesaquest.database.PlayerDB;

import java.util.logging.Logger;

public class Register {
    private static final Logger logger = Logger.getLogger("| Register | ");
    @FXML private TextField emailField;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    @FXML
    private void register(ActionEvent actionEvent) {
        Player player = new Player(emailField.getText(), usernameField.getText(), passwordField.getText());

        PlayerDB playerDB = new PlayerDB();
        playerDB.createPlayer(player);
    }
}
