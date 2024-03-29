package fractalmatic;

import fractalmatic.circles.config.ConfigurationManager;
import fractalmatic.circles.ui.CirclesMainUiController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * JavaFX App class, application starts here.
 */
public class App extends Application {

    /** The scene. */
    static Scene scene;
    /** The stage. */
    static Stage stage;
    /** The name of the fxml file that is currently shown. */
    private static String currentFxml;

    /**
     * This method changes the content of the stage/decorator, also resizes the window to the content's size
     * if the window is not fullscreen or is not maximized. If so, the current sizing does not change.
     *
     * @param fxml fxml file to be loaded to current window
     */
    public static void setRoot(String fxml) {
        String[] fullPath = fxml.split("/");
        currentFxml = fullPath[fullPath.length - 1];
        //set content
        scene.setRoot(loadFXML(fxml));
        if (!stage.isMaximized() && !stage.isFullScreen()) {
            stage.sizeToScene();
        }
    }

    /**
     * This method reads the fxml file and returns
     *
     * @param fxml the fxml file, including location and extension, to be read
     * @return the component is read from the fxml file
     */
    private static Parent loadFXML(String fxml) {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml));
        Parent parent = new Pane();
        try {
            parent = fxmlLoader.load();
            bindFullAndMaxBehaviors(fxmlLoader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return parent;
    }

    /**
     * Binds full screen and maximized behaviors.
     *
     * @param loader the loader of the resource to be used to get controller\
     */
    private static void bindFullAndMaxBehaviors(FXMLLoader loader) {
        if (currentFxml.equals("CirclesMainUi.fxml")) {
            CirclesMainUiController controller = loader.getController();
            stage.fullScreenProperty().addListener((observable, oldValue, newValue) -> controller.resetFractalCenter());
            stage.maximizedProperty().addListener(((observable, oldValue, newValue) -> controller.resetFractalCenter()));
        }
        //some other fractal behaviors
    }

    /**
     * Main method...
     *
     * @param args console arguments
     */
    public static void main(String[] args) {
        launch();
    }

    /**
     * Application starting point, gets called by Application.
     *
     * @param stage passed by Application class
     * @throws IOException the exception is thrown when problem with fxml file loading
     */
    @Override
    public void start(Stage stage) throws IOException {
        //stage/window settings
        App.stage = stage;
        stage.setTitle("FractalMatic");
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource("/fractalmatic/img/icon.png")).toExternalForm()));
        stage.setMinWidth(300); // min 300x300
        stage.setMinHeight(300);
        stage.setResizable(true);
        stage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (KeyCode.F11.equals(event.getCode())) {
                stage.setFullScreen(!stage.isFullScreen());
            }
        });
        stage.fullScreenProperty().addListener((observable, oldValue, newValue) -> {

        });
        //load first menu
        currentFxml = "Home.fxml";
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fractalmatic/Home.fxml"));
        Parent content = fxmlLoader.load();
        scene = new Scene(content);
        //fonts getting added, not sure this would work without internet connection after bundling the app
        scene.getStylesheets().add("https://fonts.googleapis.com/css2?family=JetBrains+Mono:wght@100;300;400;500;700;800&display=swap");
        // style sheet needs to be attached again, because it seems that JFXDecorator removes style sheet
        scene.getStylesheets().add(getClass().getResource("/fractalmatic/styles.css").toExternalForm());
        //ready to go
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() {
        if (currentFxml.toLowerCase().contains("circles") && currentFxml.toLowerCase().contains("main")) {
            ConfigurationManager.INSTANCE.handleSavingBeforeClosing();
        }
    }

}