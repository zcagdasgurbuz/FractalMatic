package circles.ui;

import circles.calculation.PropertyManager;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Info display controller
 */
public class CirclesInfoDisplayController {

    /** The number of drawn circles drawn circles text */
    public Text drawnCircles;
    /** The error message text field */
    public Text errorMessage;
    /** The animation status text field */
    public Text animationStatus;
    /** The animation error message text field */
    public Text animationErrorMessage;

    /**
     * Initializes before loading
     */
    @FXML
    public void initialize() {
        PropertyManager.INSTANCE.bindInfoDisplay(getTexts());
    }

    /**
     * Retrieves the text fields in the info display
     *
     * @return the text fields
     */
    public List<Text> getTexts() {
        ArrayList<Text> texts = new ArrayList<>();
        texts.add(drawnCircles);
        texts.add(errorMessage);
        texts.add(animationStatus);
        texts.add(animationErrorMessage);
        return texts;
    }
}
