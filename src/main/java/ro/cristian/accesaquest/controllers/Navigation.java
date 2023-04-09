package ro.cristian.accesaquest.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import ro.cristian.accesaquest.App;
import ro.cristian.accesaquest.util.Notification;

public class Navigation {
    @FXML
    public void loadHome(ActionEvent actionEvent) {
        App.getInstance().loadScene("home");
    }

    @FXML
    public void myProfile(ActionEvent actionEvent) {
        App.getInstance().loadScene("profile");
    }

    @FXML
    public void logout(ActionEvent actionEvent) {
        App.getInstance().setMyPlayer(null);
        Notification.showConfirmationNotification("Logout confirmation", "Log out successful");
        App.getInstance().loadScene("welcome");
    }

    @FXML
    public void leaderboard(ActionEvent actionEvent) {
        App.getInstance().loadScene("leaderboard");
    }
}
