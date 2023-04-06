package ro.cristian.accesaquest.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;
import ro.cristian.accesaquest.App;
import ro.cristian.accesaquest.database.QuestDB;

import java.net.URL;
import java.util.ResourceBundle;

public class IncompleteQuests implements Initializable {
    @FXML public ScrollPane scrollPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        QuestDB questDB = new QuestDB();
        String username = (String) App.getInstance().getMyPlayer().get("username");
        var questsIncomplete = questDB.getIncompleteQuests(username,5);

        VBox primaryVBox = new VBox();
        for(var quest : questsIncomplete){
            VBox vBox = new VBox();
            HBox hBox = new HBox();

            Label name = new Label((String) quest.get("name"));
            hBox.getChildren().add(name);

            Button button = new Button("Accept quest");
            button.setOnAction(actionEvent -> {
                //TODO accept the quest
                System.out.println("Button pressed for the quest" + quest.get("name"));
            });
            hBox.getChildren().add(button);

            vBox.getChildren().add(hBox);

            TextArea textArea = new TextArea((String) quest.get("description"));
            textArea.setWrapText(true);
            textArea.setEditable(false);

            vBox.getChildren().add(textArea);

            primaryVBox.getChildren().add(vBox);
        }

        Pane pane = new Pane();
        pane.getChildren().add(primaryVBox);
        scrollPane.setContent(pane);
    }
}
