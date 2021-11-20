package fractalmatic;

import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * Controller of the home screen/menu
 */
public class HomeController {

    /** The circles fractal info. */
    private final String CIRCLES_INFO = "F11: Fullscreen, CTRL + R: Randomize, CTRL + SHIFT + C: CaptureMode \nMouse: Drag/Zoom, Double-click: Reset position";
    /** The circles fractal logo path. */
    private final String CIRCLES_LOGO_PATH = "/fractalmatic/img/circlesBig.png";

    /** The fractal list. */
    public ListView<String> fractalList;
    /** The start button. */
    public Button startButton;
    /** The home window. */
    public StackPane homeWindow;
    /** The fractal logo viewer. */
    public ImageView fractalLogo;
    /** The fractal info text. */
    public Text infoText;
    /** The fractals list stack pane. */
    public StackPane listPane;

    /**
     * Initializes before loading
     */
    @FXML
    public void initialize() {
        fractalList.getItems().add("Circles");
        fractalList.getSelectionModel().selectedIndexProperty().addListener(((observable, oldValue, newValue) -> {
            if (fractalList.getSelectionModel().getSelectedItem().equals("Circles")) {
                //positions might need to be adjusted for other types, so, gets handled here instead of fxml
                infoText.setText(CIRCLES_INFO);
                //bottom-center
                double xPos = -(infoText.getLayoutBounds().getWidth() - listPane.getWidth()) / 2.0;
                infoText.setTranslateX(xPos);
                infoText.setTranslateY(listPane.getWidth());
                //logo center is at upper left corner of list pane.
                Image logo = new Image(getClass().getResource(CIRCLES_LOGO_PATH).toExternalForm());
                fractalLogo.setImage(logo);
                fractalLogo.setTranslateX(-(logo.getWidth() / 2.0));
                fractalLogo.setTranslateY(-(logo.getHeight() / 2.0));
                fractalLogo.setScaleX(1.5);
                fractalLogo.setScaleY(1.5);
                //rotation animation
                RotateTransition rotateTransition = new RotateTransition(Duration.millis(30000), fractalLogo);
                rotateTransition.setByAngle(1080);
                rotateTransition.setCycleCount(2);
                rotateTransition.setAutoReverse(true);
                rotateTransition.play();
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
            App.setRoot("/fractalmatic/circles/ui/CirclesMainUi.fxml");
        }
    }

}
