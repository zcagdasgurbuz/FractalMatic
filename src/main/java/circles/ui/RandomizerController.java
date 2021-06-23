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
    /** The background color check box. */
    public CheckBox backgroundColorCheckBox;
    /** The fractal color check box. */
    public CheckBox fractalColorCheckBox;
    /** The fractal final color check box. */
    public CheckBox fractalFinalColorCheckBox;
    /** The child count check box. */
    public CheckBox childCountCheckBox;
    /** The recursion depth check box. */
    public CheckBox recursionDepthCheckBox;
    /** The start angle check box. */
    public CheckBox startAngleCheckBox;
    /** The initial radius check box. */
    public CheckBox initialRadiusCheckBox;
    /** The size ratio check box. */
    public CheckBox sizeRatioCheckBox;
    /** The line width check box. */
    public CheckBox lineWidthCheckBox;
    /** The line width final check box. */
    public CheckBox lineWidthFinalCheckBox;
    /** The opacity check box. */
    public CheckBox opacityCheckBox;
    /** The opacity final check box. */
    public CheckBox opacityFinalCheckBox;
    /** The start angle animation check box. */
    public CheckBox startAngleAnimationCheckBox;
    /** The initial radius animation check box. */
    public CheckBox initialRadiusAnimationCheckBox;
    /** The size ratio animation check box. */
    public CheckBox sizeRatioAnimationCheckBox;
    /** The line width animation check box. */
    public CheckBox lineWidthAnimationCheckBox;
    /** The line width final animation check box. */
    public CheckBox lineWidthFinalAnimationCheckBox;
    /** The opacity animation check box. */
    public CheckBox opacityAnimationCheckBox;
    /** The opacity final animation check box. */
    public CheckBox opacityFinalAnimationCheckBox;
    /** The main box. */
    public HBox mainBox;
    /** The add all configs button. */
    public Button addAllConfigsButton;
    /** The remove all configs button. */
    public Button removeAllConfigsButton;
    /** The add all animations button. */
    public Button addAllAnimationsButton;
    /** The remove all animations button. */
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
