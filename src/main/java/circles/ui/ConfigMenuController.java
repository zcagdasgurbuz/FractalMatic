package circles.ui;

import circles.calculation.PropertyManager;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Control;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.text.Text;

import java.util.HashMap;
import java.util.Map;

/**
 * Configuration menu controller
 */
public class ConfigMenuController {

    /** The fractal color final check box. */
    public CheckBox fractalColorFinalCheckBox;
    /** The line width final check box. */
    public CheckBox lineWidthFinalCheckBox;
    /** The opacity final check box. */
    public CheckBox opacityFinalCheckBox;
    /** The background color picker. */
    public ColorPicker backgroundColorPicker;
    /** The fractal color picker. */
    public ColorPicker fractalColorPicker;
    /** The fractal final color picker. */
    public ColorPicker fractalFinalColorPicker;
    /** The child count spinner. */
    public Spinner<Integer> childCountSpinner;
    /** The recursions spinner. */
    public Spinner<Integer> recursionsSpinner;
    /** The start angle slider. */
    public Slider startAngleSlider;
    /** The initial radius slider. */
    public Slider initialRadiusSlider;
    /** The size ratio slider. */
    public Slider sizeRatioSlider;
    /** The line width slider. */
    public Slider lineWidthSlider;
    /** The line width final slider. */
    public Slider lineWidthFinalSlider;
    /** The opacity slider. */
    public Slider opacitySlider;
    /** The opacity final slider. */
    public Slider opacityFinalSlider;
    /** The start angle text. */
    public Text startAngleText;
    /** The initial radius text. */
    public Text initialRadiusText;
    /** The size ratio text. */
    public Text sizeRatioText;
    /** The line width text. */
    public Text lineWidthText;
    /** The line width final text. */
    public Text lineWidthFinalText;
    /** The opacity text. */
    public Text opacityText;
    /** The opacity final text. */
    public Text opacityFinalText;

    /** The Property manager. */
    PropertyManager propertyManager = PropertyManager.INSTANCE;

    /**
     * Initializes before loading
     */
    @FXML
    public void initialize() {
        //attach text fields corresponding sliders
        startAngleText.textProperty().bind(Bindings.format("%3.1f", startAngleSlider.valueProperty()));
        initialRadiusText.textProperty().bind(Bindings.format("%3.1f", initialRadiusSlider.valueProperty()));
        sizeRatioText.textProperty().bind(Bindings.format("%3.1f", sizeRatioSlider.valueProperty()));
        lineWidthText.textProperty().bind(Bindings.format("%1.2f", lineWidthSlider.valueProperty()));
        lineWidthFinalText.textProperty().bind(Bindings.format("%1.2f", lineWidthFinalSlider.valueProperty()));
        opacityText.textProperty().bind(Bindings.format("%1.2f", opacitySlider.valueProperty()));
        opacityFinalText.textProperty().bind(Bindings.format("%1.2f", opacityFinalSlider.valueProperty()));
        propertyManager.setConfigControls(getControls()); // send controls to property manager
        propertyManager.createBindings(); // let property manager create bindings
        propertyManager.addListeners();  // let property manager start listening
    }

    /**
     * This method returns a list contains all the control in the config menu
     *
     * @return all controls in the config menu
     */
    public Map<String, Control> getControls() {
        HashMap<String, Control> controls = new HashMap<>();
        controls.put("background_color_picker", backgroundColorPicker);
        controls.put("fractal_final_color_checkbox", fractalColorFinalCheckBox);
        controls.put("fractal_color_picker", fractalColorPicker);
        controls.put("fractal_final_color_picker", fractalFinalColorPicker);
        controls.put("child_count_spinner", childCountSpinner);
        controls.put("recursions_spinner", recursionsSpinner);
        controls.put("start_angle_slider", startAngleSlider);
        controls.put("initial_radius_slider", initialRadiusSlider);
        controls.put("size_ratio_slider", sizeRatioSlider);
        controls.put("line_width_final_checkbox", lineWidthFinalCheckBox);
        controls.put("line_width_slider", lineWidthSlider);
        controls.put("line_width_final_slider", lineWidthFinalSlider);
        controls.put("opacity_final_checkbox", opacityFinalCheckBox);
        controls.put("opacity_slider", opacitySlider);
        controls.put("opacity_final_slider", opacityFinalSlider);
        return controls;
    }

}
