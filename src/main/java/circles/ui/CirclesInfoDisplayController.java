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

    public Text errorMessage;
    public Text drawnCircles;
    public Text animationStatus;
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
