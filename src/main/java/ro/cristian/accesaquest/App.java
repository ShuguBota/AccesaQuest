package ro.cristian.accesaquest;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ro.cristian.accesaquest.models.Badge;

import java.io.File;
import java.io.IOException;

public class App extends Application {
    private static final Logger logger = LogManager.getLogger(App.class);
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

        primaryWindow.setWidth(getScreenWidth());
        primaryWindow.setHeight(getScreenHeight());

        primaryWindow.show();
    }

    public static Parent loadFXML(String fileName) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("ui/" + fileName + ".fxml"));
        logger.info("Loading {} fxml file", fileName);
        return fxmlLoader.load();
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
