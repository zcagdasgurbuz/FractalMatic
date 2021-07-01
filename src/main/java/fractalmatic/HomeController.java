package fractalmatic;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;

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

    /**
     * Initializes before loading
     */
    @FXML
    public void initialize() {
        fractalList.getItems().add("Circles");
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
