package ro.cristian.accesaquest.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import org.json.simple.JSONObject;
import ro.cristian.accesaquest.App;
import ro.cristian.accesaquest.database.PlayerDB;
import ro.cristian.accesaquest.models.JSON;
import ro.cristian.accesaquest.models.Player;

import java.net.URL;
import java.util.ResourceBundle;

public class Badges implements Initializable {
    @FXML public ScrollPane scrollPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Player myPlayer = JSON.deserializeJSONPlayer(App.getInstance().getMyPlayer());

        VBox primaryVBox = new VBox();
        primaryVBox.setSpacing(30);

        Label badgesLabel = new Label("Badges");
        badgesLabel.getStyleClass().add("title");
        badgesLabel.setAlignment(Pos.TOP_CENTER);
        primaryVBox.setAlignment(Pos.TOP_CENTER);
        primaryVBox.getChildren().add(badgesLabel);
        primaryVBox.setAlignment(Pos.TOP_LEFT);

        for(var badge : myPlayer.getBadges()){
            String completedBadge = "You have completed ";
            String completedTakenBadge = " taken quests";

            String completedCreatedBadge = " created quest";

            VBox badgeVBox = new VBox();
            badgeVBox.getStyleClass().add("background-single-quest");
            badgeVBox.setPadding(new Insets(0, 10, 0, 10));

            Image image = new Image(badge);
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(100);
            imageView.setFitWidth(100);
            badgeVBox.setAlignment(Pos.CENTER);
            badgeVBox.getChildren().add(imageView);

            Label descriptionLabel = new Label();
            descriptionLabel.getStyleClass().add("label-quest");
            String code = "";

            if(badge.contains("1")) code = "1";
            if(badge.contains("2")) code = "5";
            if(badge.contains("3")) code = "10";
            if(badge.contains("4")) code = "15";
            if(badge.contains("5")) code = "20";

            if(badge.contains("Taken") && badge.contains("1"))
                descriptionLabel.setText(completedBadge + code + completedTakenBadge);
            if(badge.contains("Taken") && !badge.contains("1"))
                descriptionLabel.setText(completedBadge + code + completedTakenBadge + "s");
            if(badge.contains("Created") && badge.contains("1"))
                descriptionLabel.setText(completedBadge + code + completedCreatedBadge);
            if(badge.contains("Created") && !badge.contains("1"))
                descriptionLabel.setText(completedBadge + code + completedCreatedBadge + "s");

            descriptionLabel.setTextAlignment(TextAlignment.CENTER);
            descriptionLabel.setAlignment(Pos.CENTER);
            badgeVBox.getChildren().add(descriptionLabel);

            primaryVBox.getChildren().add(badgeVBox);
        }

        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        primaryVBox.setPrefHeight(350);
        scrollPane.setContent(primaryVBox);
    }
}
