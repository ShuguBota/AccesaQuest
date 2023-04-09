package ro.cristian.accesaquest.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import ro.cristian.accesaquest.App;

import java.util.logging.Logger;

public class Notification {
    private static final Logger logger = Logger.getLogger("| Notification | ");
    public static void showErrorNotificationAndExit(Exception e) {
        createNotification(Alert.AlertType.ERROR, "Error", "An Error has occurred!", e.toString()).showAndWait();
        e.printStackTrace();
        App.getInstance().hardShutdown();
    }

    public static void showErrorNotification(Exception e) {
        createNotification(Alert.AlertType.ERROR, "Error", "An Error has occurred!", e.toString()).showAndWait();
    }

    public static void showErrorNotification(String s) {
        createNotification(Alert.AlertType.ERROR, "Error", "An Error has occurred!", s).showAndWait();
    }

    public static void showInfoNotification(String title, String headerText, String contentText) {
        createNotification(Alert.AlertType.INFORMATION, title, headerText, contentText).showAndWait();
    }

    public static void showConfirmationNotification(String headerText, String contentText) {
        createNotification(Alert.AlertType.CONFIRMATION, "Confirmation", headerText, contentText).showAndWait();
    }

    public static void showConfirmationNotificationWithCode(String headerText, String contentText, Runnable runnable) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, headerText, ButtonType.YES, ButtonType.CANCEL);
        alert.setTitle("Confirmation");
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(App.class.getResource("style/main.css").toExternalForm());
        dialogPane.getStyleClass().add("notifications");

        alert.showAndWait();

        ButtonType type = alert.getResult();
        if(!type.getButtonData().isCancelButton())
            runnable.run();
    }

    public static void showWarningNotification(String headerText, String contentText) {
        createNotification(Alert.AlertType.WARNING, "Warning", headerText, contentText).showAndWait();
    }

    private static Alert createNotification(Alert.AlertType type, String title, String headerText, String contentText) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(App.class.getResource("style/main.css").toExternalForm());
        dialogPane.getStyleClass().add("notifications");
        logger.info("Showing alert" + contentText);
        return alert;
    }

}
