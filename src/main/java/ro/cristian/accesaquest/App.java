package ro.cristian.accesaquest;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.json.simple.JSONObject;
import ro.cristian.accesaquest.models.Player;
import ro.cristian.accesaquest.util.Notification;

import java.io.IOException;
import java.util.logging.Logger;

public class App extends Application {
    private static final Logger logger = Logger.getLogger("| App | ");
    private static App appInstance;

    private Stage primaryWindow;
    private Scene scene;

    private JSONObject myPlayer = null;

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
        setDimensions();

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

    public void setDimensions() {
        var oldHeight = primaryWindow.getHeight();
        var oldWidth = primaryWindow.getWidth();

        //Change a bit the values to center stuff properly
        primaryWindow.setWidth(oldWidth - 1.0);
        primaryWindow.setWidth(oldWidth + 1.0);
        primaryWindow.setHeight(oldHeight - 1.0);
        primaryWindow.setHeight(oldHeight + 1.0);

        primaryWindow.show();
    }

    public void loadScene(String fileName){
        try {
            Scene loginScene = new Scene(App.loadFXML(fileName));
            App.getInstance().getStage().setScene(loginScene);
            App.getInstance().setDimensions();
        } catch (IOException e){
            logger.info("The login fxml file couldn't be loaded");
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch();
    }

    public static double getScreenWidth(){
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        return screenBounds.getWidth();
    }

    public static double getScreenHeight(){
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        return screenBounds.getHeight();
    }

    public Stage getStage(){
        return this.primaryWindow;
    }

    public static App getInstance(){
        return appInstance;
    }

    public JSONObject getMyPlayer() {
        return myPlayer;
    }

    public void setMyPlayer(JSONObject myPlayer) {
        this.myPlayer = myPlayer;
    }

    public void openWindow(String title, String fxmlFile, double size) {
        try {
            Stage window = new Stage();
            final int screenSize = (int) Math.round(App.getScreenHeight() * size);
            Parent parent = App.loadFXML(fxmlFile);
            Scene scene = new Scene(parent, screenSize, screenSize);
            window.setTitle(title);
            window.setScene(scene);
            window.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
