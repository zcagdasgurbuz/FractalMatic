package circles.ui;

import circles.util.ConfigurationManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;

import java.util.HashMap;

/**
 * Randomizer's settings controller
 */
public class RandomizerController {
    public CheckBox backgroundColorCheckBox;
    public CheckBox fractalColorCheckBox;
    public CheckBox fractalFinalColorCheckBox;
    public CheckBox childCountCheckBox;
    public CheckBox recursionDepthCheckBox;
    public CheckBox startAngleCheckBox;
    public CheckBox initialRadiusCheckBox;
    public CheckBox sizeRatioCheckBox;
    public CheckBox lineWidthCheckBox;
    public CheckBox lineWidthFinalCheckBox;
    public CheckBox opacityCheckBox;
    public CheckBox opacityFinalCheckBox;
    public CheckBox startAngleAnimationCheckBox;
    public CheckBox initialRadiusAnimationCheckBox;
    public CheckBox sizeRatioAnimationCheckBox;
    public CheckBox lineWidthAnimationCheckBox;
    public CheckBox lineWidthFinalAnimationCheckBox;
    public CheckBox opacityAnimationCheckBox;
    public CheckBox opacityFinalAnimationCheckBox;
    public HBox mainBox;
    public Button addAllConfigsButton;
    public Button removeAllConfigsButton;
    public Button addAllAnimationsButton;
    public Button removeAllAnimationsButton;

    /**
     * Initializes before loading
     */
    @FXML
    public void initialize() {
        HashMap<String, CheckBox> allCheckBoxes = new HashMap<>();
        allCheckBoxes.put("backgroundColorCheckBox", backgroundColorCheckBox);
        allCheckBoxes.put("fractalColorCheckBox", fractalColorCheckBox);
        allCheckBoxes.put("fractalFinalColorCheckBox", fractalFinalColorCheckBox);
        allCheckBoxes.put("childCountCheckBox", childCountCheckBox);
        allCheckBoxes.put("recursionDepthCheckBox", recursionDepthCheckBox);
        allCheckBoxes.put("startAngleCheckBox", startAngleCheckBox);
        allCheckBoxes.put("initialRadiusCheckBox", initialRadiusCheckBox);
        allCheckBoxes.put("sizeRatioCheckBox", sizeRatioCheckBox);
        allCheckBoxes.put("lineWidthCheckBox", lineWidthCheckBox);
        allCheckBoxes.put("lineWidthFinalCheckBox", lineWidthFinalCheckBox);
        allCheckBoxes.put("opacityCheckBox", opacityCheckBox);
        allCheckBoxes.put("opacityFinalCheckBox", opacityFinalCheckBox);
        allCheckBoxes.put("startAngleAnimationCheckBox", startAngleAnimationCheckBox);
        allCheckBoxes.put("initialRadiusAnimationCheckBox", initialRadiusAnimationCheckBox);
        allCheckBoxes.put("sizeRatioAnimationCheckBox", sizeRatioAnimationCheckBox);
        allCheckBoxes.put("lineWidthAnimationCheckBox", lineWidthAnimationCheckBox);
        allCheckBoxes.put("lineWidthFinalAnimationCheckBox", lineWidthFinalAnimationCheckBox);
        allCheckBoxes.put("opacityAnimationCheckBox", opacityAnimationCheckBox);
        allCheckBoxes.put("opacityFinalAnimationCheckBox", opacityFinalAnimationCheckBox);

        ConfigurationManager.INSTANCE.setRandomizerCheckBoxes(allCheckBoxes);
    }

    /**
     * Handles the add all configurations button function
     */
    @FXML
    public void addAllConfigsButtonAction() {
        setConfigurationCheckBoxes(true);
    }

    /**
     * Handles the remove all configurations button function
     */
    @FXML
    public void removeAllConfigsButtonAction() {
        setConfigurationCheckBoxes(false);
    }

    /**
     * Handles the add all animations button function
     */
    @FXML
    public void addAllAnimationsButtonAction() {
        setAnimationCheckBoxes(true);
    }

    /**
     * Handles the remove all animations button function
     */
    @FXML
    public void removeAllAnimationsButtonAction() {
        setAnimationCheckBoxes(false);
    }

    /**
     * Sets selected property of all configurations checkbox
     *
     * @param selected whether the checkboxes are selected
     */
    private void setConfigurationCheckBoxes(boolean selected) {
        backgroundColorCheckBox.setSelected(selected);
        fractalColorCheckBox.setSelected(selected);
        fractalFinalColorCheckBox.setSelected(selected);
        childCountCheckBox.setSelected(selected);
        recursionDepthCheckBox.setSelected(selected);
        startAngleCheckBox.setSelected(selected);
        initialRadiusCheckBox.setSelected(selected);
        sizeRatioCheckBox.setSelected(selected);
        lineWidthCheckBox.setSelected(selected);
        lineWidthFinalCheckBox.setSelected(selected);
        opacityCheckBox.setSelected(selected);
        opacityFinalCheckBox.setSelected(selected);
    }

    /**
     * Sets selected property of all animations checkbox
     *
     * @param selected whether the checkboxes are selected
     */
    private void setAnimationCheckBoxes(boolean selected) {
        startAngleAnimationCheckBox.setSelected(selected);
        initialRadiusAnimationCheckBox.setSelected(selected);
        sizeRatioAnimationCheckBox.setSelected(selected);
        lineWidthAnimationCheckBox.setSelected(selected);
        lineWidthFinalAnimationCheckBox.setSelected(selected);
        opacityAnimationCheckBox.setSelected(selected);
        opacityFinalAnimationCheckBox.setSelected(selected);
    }
}
