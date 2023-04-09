package ro.cristian.accesaquest.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.json.simple.JSONObject;
import ro.cristian.accesaquest.database.PlayerDB;
import ro.cristian.accesaquest.util.Notification;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Leaderboard implements Initializable {
    @FXML public ScrollPane scrollPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        PlayerDB playerDB = new PlayerDB();

        List<JSONObject> topRanks = null;
        try {
            topRanks = playerDB.findTop(10);
        } catch(Exception e) {
            Notification.showErrorNotification(e.getMessage());
        }

        if(topRanks == null) return;

        VBox primaryVBox = new VBox();
        primaryVBox.setSpacing(100);

        int rank = 1;

        for(var player : topRanks) {
            HBox playerHBox = new HBox();
            playerHBox.getStyleClass().add("background-single-quest");
            playerHBox.setPadding(new Insets(0, 10, 0, 10));

            Label posLabel = new Label(String.valueOf(rank));
            posLabel.getStyleClass().add("label-quest");
            playerHBox.getChildren().add(posLabel);

            Label fakeLabelLeft = new Label();
            HBox.setHgrow(fakeLabelLeft, Priority.ALWAYS);
            fakeLabelLeft.setMaxWidth(Integer.MAX_VALUE);
            playerHBox.getChildren().add(fakeLabelLeft);

            Label usernameLabel = new Label((String) player.get("username"));
            usernameLabel.getStyleClass().add("label-quest");
            playerHBox.getChildren().add(usernameLabel);

            Label fakeLabelRight = new Label();
            HBox.setHgrow(fakeLabelRight, Priority.ALWAYS);
            fakeLabelRight.setMaxWidth(Integer.MAX_VALUE);
            playerHBox.getChildren().add(fakeLabelRight);

            Label rankLabel = new Label("Rank: " + player.get("rank"));
            rankLabel.getStyleClass().add("label-quest");
            playerHBox.getChildren().add(rankLabel);

            primaryVBox.getChildren().add(playerHBox);
            rank++;
        }

        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        primaryVBox.setPrefHeight(350);
        scrollPane.setContent(primaryVBox);
    }
}
