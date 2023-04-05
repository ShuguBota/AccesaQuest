package ro.cristian.accesaquest;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

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

        primaryWindow.setWidth(getScreenWidth());
        primaryWindow.setHeight(getScreenHeight());

        primaryWindow.show();
    }

    public static Parent loadFXML(String fileName) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("ui/" + fileName + ".fxml"));
        logger.info("Loading " + fileName + " fxml file");
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
