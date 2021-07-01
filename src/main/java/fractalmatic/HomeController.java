package fractalmatic;

import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

/**
 * Controller of the home screen/menu
 */
public class HomeController {

    /** The fractal list. */
    public ListView<String> fractalList;
    /** The start button. */
    public Button startButton;
    /** The home window. */
    public StackPane homeWindow;
    public ImageView fractalLogo;

    /**
     * Initializes before loading
     */
    @FXML
    public void initialize() {
        fractalList.getItems().add("Circles");
        fractalList.getSelectionModel().selectedIndexProperty().addListener(((observable, oldValue, newValue) -> {
            if (fractalList.getSelectionModel().getSelectedItem().equals("Circles")) {
                fractalLogo.setImage(new Image(getClass().getResource("/fractalmatic/circlesBig.png").toExternalForm()));
                fractalLogo.setTranslateX(-200);
                fractalLogo.setTranslateY(-200);
                fractalLogo.setScaleX(1.5);
                fractalLogo.setScaleY(1.5);
                RotateTransition rt = new RotateTransition(Duration.millis(30000), fractalLogo);
                rt.setByAngle(1080);
                rt.setCycleCount(2);
                rt.setAutoReverse(true);
                rt.play();
            }
        }));
    }

    /**
     * This method handles the start button
     */
    @FXML
    private void handleStartButton() {
        //check listview and decide application
        if (fractalList.getSelectionModel().getSelectedItem() != null &&
                fractalList.getSelectionModel().getSelectedItem().equals("Circles")) {
            App.setRoot("/circles/ui/CirclesMainUi.fxml");
        }
    }

}
