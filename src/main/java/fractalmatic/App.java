package fractalmatic;

import circles.util.ConfigurationManager;
import com.jfoenix.controls.JFXDecorator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App class, application starts here.
 */
public class App extends Application {

    /** The decorator - fancy window. */
    private static JFXDecorator decorator;
    /** The name of the fxml file that is currently shown. */
    private static String currentFxml;

    /**
     * Application starting point, gets called by Application.
     *
     * @param stage passed by Application class
     * @throws IOException  the exception is thrown when problem with fxml file loading
     */
    @Override
    public void start(Stage stage) throws IOException {
        //stage/window settings
        stage.setTitle("FractalMatic");
        stage.setMinWidth(300); // min 300x300
        stage.setMinHeight(300);
        stage.setResizable(false); // no resizing, including maximize and full screen button
        //load first menu
        currentFxml = "Home.fxml";
        Parent content = FXMLLoader.load(getClass().getResource("/fractalmatic/Home.fxml"));
        //change the stage style with jfoenix
        decorator = new JFXDecorator(stage, content);
        decorator.setCustomMaximize(false);
        Scene scene = new Scene(decorator);
        //fonts getting added, not sure this would work without internet connection after bundling the app
        scene.getStylesheets().add("https://fonts.googleapis.com/css2?family=JetBrains+Mono:wght@100;300;400;500;700;800&display=swap");
        // style sheet needs to be attached again, because it seems that JFXDecorator removes style sheet
        scene.getStylesheets().add(getClass().getResource("/circles/ui/circles.css").toExternalForm());
        //ready to go
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method changes the content of the stage/decorator, also resizes the window to the content's size
     * if the window is not fullscreen or is not maximized. If so, the current sizing does not change.
     *
     * @param fxml  fxml file to be loaded to current window
     */
    public static void setRoot(String fxml){
        boolean isMaximized = ((Stage)decorator.getScene().getWindow()).isMaximized();
        boolean isFullScreen = ((Stage)decorator.getScene().getWindow()).isFullScreen();
        //save current fxml file name
        String[] fullPath = fxml.split("/");
        currentFxml = fullPath[fullPath.length -1];
        //set content
        decorator.setContent(loadFXML(fxml));
        if(!isMaximized && !isFullScreen){
            decorator.getScene().getWindow().sizeToScene();
        }
    }

    /**
     * This method changes the resizeable state of the window
     *
     * @param resizeable new resizeable state
     */
    public static void setResizeable(boolean resizeable){
        ((Stage)decorator.getScene().getWindow()).setResizable(resizeable);
    }

    /**
     * This method reads the fxml file and returns
     *
     * @param fxml  the fxml file, including location and extension, to be read
     * @return  the component is read from the fxml file
     */
    private static Parent loadFXML(String fxml) {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml));
        Parent parent = new Pane();
        try {
            parent = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return parent;
    }

    @Override
    public void stop(){
        if(currentFxml.toLowerCase().contains("circles") && currentFxml.toLowerCase().contains("main")){
            ConfigurationManager.INSTANCE.handleSavingBeforeClosing();
        }
    }

    /**
     * Main method...
     *
     * @param args  console arguments
     */
    public static void main(String[] args) {
        System.out.println();
        launch();
    }

}