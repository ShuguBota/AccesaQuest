package ro.cristian.accesaquest;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import ro.cristian.accesaquest.util.Notification;

import java.io.IOException;
import java.util.logging.Logger;

public class App extends Application {
    private static final Logger logger = Logger.getLogger("| App | ");
    private static App appInstance;

    private Stage primaryWindow;
    private Scene scene;

    @Override
    public void start(Stage primaryStage) throws Exception {
        logger.info("Application starting");
        appInstance = this;
        this.primaryWindow = primaryStage;

        scene = new Scene(loadFXML("welcome"));
        primaryWindow.setTitle("Accesa Quest");
        primaryWindow.setScene(scene);

        primaryWindow.setWidth(getScreenWidth()/1.5);
        primaryWindow.setHeight(getScreenHeight()/1.5);
        primaryWindow.show();

        setOnExit();
    }

    public static Parent loadFXML(String fileName) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("ui/" + fileName + ".fxml"));
        logger.info("Loading " + fileName + " fxml file");
        return fxmlLoader.load();
    }

    public void setOnExit() {
        getStage().setOnCloseRequest(event -> {
            event.consume();
            softShutdown();
        });
    }

    public void hardShutdown() {
        logger.info("Shutting down app!");
        Platform.exit();
        System.exit(0);
    }

    /**
     * Shutdown the Application.
     */
    public void softShutdown() {
        Notification.showConfirmationNotificationWithCode("Exit Application", "Are you sure you want to exit the application? All your progress will be erased!", this::hardShutdown);
    }


    public static void main(String[] args) {
        launch();
    }

    public static int getScreenWidth(){
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        return (int) (screenBounds.getWidth());
    }

    public static int getScreenHeight(){
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        return (int) (screenBounds.getHeight());
    }

    public Stage getStage(){
        return this.primaryWindow;
    }

    public static App getInstance(){
        return appInstance;
    }
}
