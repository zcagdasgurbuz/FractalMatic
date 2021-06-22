package circles.ui;

import circles.calculation.PropertyManager;
import circles.util.ConfigurationManager;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Configuration menu controller
 */
public class ConfigMenuController {

    public CheckBox fractalColorFinalCheckBox;
    public CheckBox lineWidthFinalCheckBox;
    public CheckBox opacityFinalCheckBox;
    public ColorPicker backgroundColorPicker;
    public ColorPicker fractalColorPicker;
    public ColorPicker fractalFinalColorPicker;
    public Spinner<Integer> childCountSpinner;
    public Spinner<Integer> recursionsSpinner;
    public Slider startAngleSlider;
    public Slider initialRadiusSlider;
    public Slider sizeRatioSlider;
    public Slider lineWidthSlider;
    public Slider lineWidthFinalSlider;
    public Slider opacitySlider;
    public Slider opacityFinalSlider;
    public Text startAngleText;
    public Text initialRadiusText;
    public Text sizeRatioText;
    public Text lineWidthText;
    public Text lineWidthFinalText;
    public Text opacityText;
    public Text opacityFinalText;


    PropertyManager propertyManager = PropertyManager.INSTANCE;

    /**
     * Initializes before loading
     */
    @FXML
    public void initialize(){
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
    public List<Control> getControls(){
        ArrayList<Control> controls = new ArrayList<>();
        controls.add(backgroundColorPicker);
        controls.add(fractalColorFinalCheckBox);
        controls.add(fractalColorPicker);
        controls.add(fractalFinalColorPicker);
        controls.add(childCountSpinner);
        controls.add(recursionsSpinner);
        controls.add(startAngleSlider);
        controls.add(initialRadiusSlider);
        controls.add(sizeRatioSlider);
        controls.add(lineWidthFinalCheckBox);
        controls.add(lineWidthSlider);
        controls.add(lineWidthFinalSlider);
        controls.add(opacityFinalCheckBox);
        controls.add(opacitySlider);
        controls.add(opacityFinalSlider);
        return controls;
    }

}
