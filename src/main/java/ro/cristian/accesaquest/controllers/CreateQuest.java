package ro.cristian.accesaquest.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import ro.cristian.accesaquest.App;
import ro.cristian.accesaquest.database.QuestDB;
import ro.cristian.accesaquest.models.Quest;
import ro.cristian.accesaquest.util.Notification;

public class CreateQuest {
    @FXML public TextArea descriptionLabel;
    @FXML public TextField nameLabel;

    @FXML
    public void createQuest(ActionEvent actionEvent) {
        Quest newQuest = new Quest(nameLabel.getText(), descriptionLabel.getText(), App.getInstance().getMyPlayer());

        QuestDB questDB = new QuestDB();
        boolean res;

        try {
            res = questDB.createQuest(newQuest);
        }catch(Exception e){
            Notification.showErrorNotification(e.getMessage());
            return;
        }

        if(res){
            Notification.showConfirmationNotification("Quest confirmation", "Quest created successfully");
        }
    }
}
