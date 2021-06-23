package circles.calculation;

import circles.animation.Animatable;
import circles.animation.AnimatableRangedDoubleProperty;
import circles.animation.AnimationManager;
import circles.animation.OscillationBehavior;
import circles.util.CirclesConfiguration;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.IntegerBinding;
import javafx.beans.binding.When;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Control;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.List;
import java.util.Map;

/**
 * This singleton class handles the bindings of controls and some various calculations
 */
public enum PropertyManager {
    /** The singleton instance  of property manager */
    INSTANCE; //singleton manager

    /** The  debug info flag */
    private final boolean VERBOSE = false;

    /** The background color prop. */
    private final ObjectProperty<Color> backgroundColor;
    /** The fractal color prop. */
    private final ObjectProperty<Color> fractalColor;
    /** The fractal final color  prop. */
    private final ObjectProperty<Color> fractalFinalColor;
    /** The child count object prop -necessary for spinner */
    private final ObjectProperty<Integer> childCountObjectProp;
    /** The child count prop */
    private final IntegerProperty childCount;
    /** The recursion depth object prop -necessary for spinner */
    private final ObjectProperty<Integer> recursionDepthObjectProp;
    /** The recursion depth prop */
    private final IntegerProperty recursionDepth;
    /** The start angle prop */
    private final AnimatableRangedDoubleProperty startAngle;
    /** The initial radius prop */
    private final AnimatableRangedDoubleProperty initialRadius;
    /** The size ratio prop */
    private final AnimatableRangedDoubleProperty sizeRatio;
    /** The line width prop */
    private final AnimatableRangedDoubleProperty lineWidth;
    /** The line width final prop */
    private final AnimatableRangedDoubleProperty lineWidthFinal;
    /** The opacity prop */
    private final AnimatableRangedDoubleProperty opacity;
    /** The opacity final prop */
    private final AnimatableRangedDoubleProperty opacityFinal;
    /** The maximum number of allowed recursions -based on circle radius */
    private final IntegerBinding maxAllowedRecursions;
    /** The maximum number of circles limit */
    private final IntegerProperty maximumCirclesLimit;
    /** The number of circles to be drawn -generated by current configuration */
    private final DoubleBinding numberOfCirclesToBeDrawn;
    /** The ok to draw prop - whether if the number of circles to be generated is below the limit */
    private final BooleanBinding okToDraw;
    /** The default change listener */
    private final ChangeListener<Object> changeListener;
    /** The drawing enabled property */
    private final BooleanProperty drawingEnabledProperty;
    /** The calculation manager instance */
    CalculationManager calculationManager = CalculationManager.INSTANCE;
    /** The animation manager instance */
    AnimationManager animationManager = AnimationManager.INSTANCE;
    /** The fractal color final check box */
    private CheckBox fractalColorFinalCheckBox;
    /** The line width final check box from ui */
    private CheckBox lineWidthFinalCheckBox;
    /** The opacity final check box from ui */
    private CheckBox opacityFinalCheckBox;
    /** The all control from configuration menu */
    private Map<String, Control> configControls;
    /** The all animatables */
    private final ObservableList<AnimatableRangedDoubleProperty> animatableList;

    /**
     * Constructor, initializes the fields
     */
    PropertyManager() {
        changeListener = (observableValue, o, t1) -> {
            drawRequest();
            if (VERBOSE) System.out.println("draw requested in property manager - change listener");
        };
        animatableList = FXCollections.observableArrayList();
        drawingEnabledProperty = new SimpleBooleanProperty(true);

        //initialize the fields
        backgroundColor = new SimpleObjectProperty<>();
        fractalColor = new SimpleObjectProperty<>();
        fractalFinalColor = new SimpleObjectProperty<>();
        childCountObjectProp = new SimpleObjectProperty<>(3);
        childCount = IntegerProperty.integerProperty(childCountObjectProp);
        recursionDepthObjectProp = new SimpleObjectProperty<>(5);
        recursionDepth = IntegerProperty.integerProperty(recursionDepthObjectProp);
        startAngle = new AnimatableRangedDoubleProperty(-15000, 15000, 90, "Start Angle");
        startAngle.setAnimationBehavior(new OscillationBehavior(1, 1));
        animatableList.add(startAngle);
        initialRadius = new AnimatableRangedDoubleProperty(5, 200, 100, "Initial Radius");
        initialRadius.setAnimationBehavior(new OscillationBehavior(1, 1));
        animatableList.add(initialRadius);
        sizeRatio = new AnimatableRangedDoubleProperty(5, 70, 50, "Size Ratio");
        sizeRatio.setAnimationBehavior(new OscillationBehavior(1, 1));
        animatableList.add(sizeRatio);
        lineWidth = new AnimatableRangedDoubleProperty(0.1, 10, 1, "Line Width");
        lineWidth.setAnimationBehavior(new OscillationBehavior(1, 1));
        animatableList.add(lineWidth);
        lineWidthFinal = new AnimatableRangedDoubleProperty(0.1, 10, 1, "Final Line Width");
        lineWidthFinal.setAnimationBehavior(new OscillationBehavior(1, 1));
        animatableList.add(lineWidthFinal);
        opacity = new AnimatableRangedDoubleProperty(0.01, 1, 1, "Opacity");
        opacity.setAnimationBehavior(new OscillationBehavior(1, 1));
        animatableList.add(opacity);
        opacityFinal = new AnimatableRangedDoubleProperty(0.01, 1, 1, "Final Opacity");
        opacityFinal.setAnimationBehavior(new OscillationBehavior(1, 1));
        animatableList.add(opacityFinal);

        maximumCirclesLimit = new SimpleIntegerProperty();
        maxAllowedRecursions = new IntegerBinding() {
            {
                super.bind(initialRadius, sizeRatio, recursionDepth);
            }

            @Override
            protected int computeValue() {
                return calculationManager.calculateExpectedRecursionDepth(initialRadius.getValue(), recursionDepth.get(),
                        sizeRatio.getValue());
            }
        };
        numberOfCirclesToBeDrawn = new DoubleBinding() {
            {
                super.bind(maxAllowedRecursions, childCount);
            }

            @Override
            protected double computeValue() {
                return calculationManager.calculateExpectedCirclesCount(maxAllowedRecursions.get(), childCount.getValue());
            }
        };
        okToDraw = new When(numberOfCirclesToBeDrawn.greaterThan(maximumCirclesLimit).or(numberOfCirclesToBeDrawn.lessThan(0)))
                .then(false).otherwise(true);

        ObservableList<Animatable> copyList = FXCollections.observableArrayList(animatableList);
        animationManager.setAvailableAnimatableList(copyList);

    }

    /**
     * This method sets all controls of the config menu in the properties, to bind
     *
     * @param controls all control of config menu
     */
    public void setConfigControls(Map<String, Control> controls) {
        configControls = controls;
    }

    /**
     * This method creates all the control bindings, needs to be called after setting the controls
     */
    @SuppressWarnings("unchecked")
    public void createBindings() {
        ((ColorPicker) configControls.get("background_color_picker")).valueProperty().bindBidirectional(backgroundColor);
        ((ColorPicker) configControls.get("fractal_color_picker")).valueProperty().bindBidirectional(fractalColor);
        ((ColorPicker) configControls.get("fractal_final_color_picker")).valueProperty().bindBidirectional(fractalFinalColor);
        this.fractalColorFinalCheckBox = (CheckBox) configControls.get("fractal_final_color_checkbox");
        ((Spinner<Integer>) configControls.get("child_count_spinner"))
                .getValueFactory().valueProperty().bindBidirectional(childCountObjectProp);
        ((Spinner<Integer>) configControls.get("recursions_spinner"))
                .getValueFactory().valueProperty().bindBidirectional(recursionDepthObjectProp);
        ((Slider) configControls.get("start_angle_slider")).valueProperty().bindBidirectional(startAngle);
        ((Slider) configControls.get("initial_radius_slider")).valueProperty().bindBidirectional(initialRadius);
        ((Slider) configControls.get("size_ratio_slider")).valueProperty().bindBidirectional(sizeRatio);
        ((Slider) configControls.get("line_width_slider")).valueProperty().bindBidirectional(lineWidth);
        ((Slider) configControls.get("line_width_final_slider")).valueProperty().bindBidirectional(lineWidthFinal);
        this.lineWidthFinalCheckBox = (CheckBox) configControls.get("line_width_final_checkbox");
        ((Slider) configControls.get("opacity_slider")).valueProperty().bindBidirectional(opacity);
        ((Slider) configControls.get("opacity_final_slider")).valueProperty().bindBidirectional(opacityFinal);
        this.opacityFinalCheckBox = (CheckBox) configControls.get("opacity_final_checkbox");
    }

    /**
     * This method sets the configuration
     *
     * @param configuration the configuration to be set
     */
    public void setConfiguration(CirclesConfiguration configuration) {
        maximumCirclesLimit.set(configuration.getConfigurationMaximumCircles());
        animationManager.reset();
        backgroundColor.set(configuration.getBackgroundColor());
        fractalColorFinalCheckBox.setSelected(configuration.isFractalFinalColorActive());
        fractalColor.set(configuration.getFractalColor());
        fractalFinalColor.set(configuration.getFractalFinalColor());
        childCount.set(configuration.getChildCount());
        recursionDepth.set(configuration.getRecursions());
        startAngle.set(configuration.getStartAngle());
        initialRadius.set(configuration.getInitialRadius());
        sizeRatio.set(configuration.getSizeRatio());
        lineWidthFinalCheckBox.setSelected(configuration.isLineWidthFinalActive());
        lineWidth.set(configuration.getLineWidth());
        lineWidthFinal.set(configuration.getLineWidthFinal());
        opacityFinalCheckBox.setSelected(configuration.isOpacityFinalActive());
        opacity.set(configuration.getOpacity());
        opacityFinal.set(configuration.getOpacityFinal());
        //animations
        animationManager.setCurrentConfig(configuration);
    }

    /**
     * This method retrieves the current configuration
     *
     * @return the current configuration
     */
    public CirclesConfiguration getCurrentConfiguration() {
        CirclesConfiguration currentConfig = new CirclesConfiguration();
        getCurrentConfiguration(currentConfig);
        return currentConfig;
    }

    /**
     * This method retrieves the current configuration, writes the current config on passed configuration
     *
     * @param config the config to be written on
     */
    public void getCurrentConfiguration(CirclesConfiguration config) {

        config.setConfigurationMaximumCircles(maximumCirclesLimit.get());
        config.setBackgroundColor(backgroundColor.get());
        config.setFractalFinalColorActive(fractalColorFinalCheckBox.isSelected());
        config.setFractalColor(fractalColor.get());
        config.setFractalFinalColor(fractalFinalColor.get());
        config.setChildCount(childCount.get());
        config.setRecursions(recursionDepth.get());
        config.setStartAngle(startAngle.get());
        config.setInitialRadius(initialRadius.get());
        config.setSizeRatio(sizeRatio.get());
        config.setLineWidthFinalActive(lineWidthFinalCheckBox.isSelected());
        config.setLineWidth(lineWidth.get());
        config.setLineWidthFinal(lineWidthFinal.get());
        config.setOpacityFinalActive(opacityFinalCheckBox.isSelected());
        config.setOpacity(opacity.get());
        config.setOpacityFinal(opacityFinal.get());
        //hand over to animation manager
        animationManager.getCurrentConfig(config);
    }

    /**
     * Sends an indirect draw request, does not guarantee request will be fulfilled
     */
    public void drawRequest() {
        if (drawingEnabledProperty.get()) {
            Platform.runLater(() -> calculationManager.drawRequest(backgroundColor.get(),
                    fractalColorFinalCheckBox.isSelected(),
                    fractalColor.get(),
                    fractalFinalColor.get(),
                    childCount.get(),
                    maxAllowedRecursions.get(),
                    startAngle.get(),
                    initialRadius.get(),
                    sizeRatio.get(),
                    lineWidthFinalCheckBox.isSelected(),
                    lineWidth.get(), lineWidthFinal.get(),
                    opacityFinalCheckBox.isSelected(),
                    opacity.get(),
                    opacityFinal.get()));
        }
    }

    /**
     * Adds default listener to all the config controls
     */
    public void addListeners() {
        addListeners(changeListener);
    }

    /**
     * Adds custom listeners to all the config controls
     */
    public void addListeners(ChangeListener<Object> customChangeListener) {
        backgroundColor.addListener(customChangeListener);
        fractalColor.addListener(customChangeListener);
        fractalFinalColor.addListener(customChangeListener);
        fractalColorFinalCheckBox.selectedProperty().addListener(customChangeListener);
        childCount.addListener(customChangeListener);
        recursionDepth.addListener(customChangeListener);
        startAngle.addListener(customChangeListener);
        initialRadius.addListener(customChangeListener);
        sizeRatio.addListener(customChangeListener);
        lineWidth.addListener(customChangeListener);
        lineWidthFinal.addListener(customChangeListener);
        lineWidthFinalCheckBox.selectedProperty().addListener(customChangeListener);
        opacity.addListener(customChangeListener);
        opacityFinal.addListener(customChangeListener);
        opacityFinalCheckBox.selectedProperty().addListener(customChangeListener);
    }

    /**
     * Removes default listener from all the config controls
     */
    public void removeListeners() {
        removeListeners(changeListener);
    }

    /**
     * Removes the listener from all the config controls
     */
    public void removeListeners(ChangeListener<Object> customChangeListener) {
        backgroundColor.removeListener(customChangeListener);
        fractalColor.removeListener(customChangeListener);
        fractalFinalColor.removeListener(customChangeListener);
        fractalColorFinalCheckBox.selectedProperty().removeListener(customChangeListener);
        childCount.removeListener(customChangeListener);
        recursionDepth.removeListener(customChangeListener);
        startAngle.removeListener(customChangeListener);
        initialRadius.removeListener(customChangeListener);
        sizeRatio.removeListener(customChangeListener);
        lineWidth.removeListener(customChangeListener);
        lineWidthFinal.removeListener(customChangeListener);
        lineWidthFinalCheckBox.selectedProperty().removeListener(customChangeListener);
        opacity.removeListener(customChangeListener);
        opacityFinal.removeListener(customChangeListener);
        opacityFinalCheckBox.selectedProperty().removeListener(customChangeListener);
    }

    /**
     * Returns if the expected circles are in allowed range
     *
     * @return true if the number of circles is in allowed range
     */
    public boolean getOkToDraw() {
        return okToDraw.get();
    }

    /**
     * Binds all the text fields of the info display
     *
     * @param texts all texts of the info display
     */
    public void bindInfoDisplay(List<Text> texts) {

        for (Text text : texts) {
            String id = text.getId().toLowerCase();

            if (id.contains("drawn")) {
                text.textProperty().bind(calculationManager.getGenerator().drawnCircles().asString());
            } else if (id.contains("error") && !id.contains("animation")) {
                text.textProperty().bind(Bindings.concat(Bindings.format("%.0f", numberOfCirclesToBeDrawn),
                        " circles are requested, which exceed the limit."));
                text.visibleProperty().bind(Bindings.not(okToDraw));
            } else if (id.contains("animation") && id.contains("status")) {
                text.textProperty().bind(Bindings.when(animationManager.getAnimator().activeProperty())
                        .then(Bindings.concat("# of maximum circles ", animationManager.getAnimationCirclesProperty().asString()))
                        .otherwise("Standby"));
            } else if (id.contains("animation") && id.contains("error")) {
                text.visibleProperty().bind(animationManager.haltedProperty());
                text.textProperty().set("Animation is halted! # of maximum circles is too large!" +
                        " Try to reduce amplitudes or remove some elements");
            }
        }
    }

    /**
     * Calculates the maximum number of circles, includes animation amplitudes
     *
     * @return the maximum number of circles that is going to be drawn during the animation
     */
    public int getAnimationMaxCircleCount() {
        double initialRadiusValue;
        //see if initial radius animation is active, if so calculate possible initial radius - base value + amplitude
        if (initialRadius.getActiveStatus()) {
            initialRadiusValue = initialRadius.getAnimationBaseValue().doubleValue()
                    + AnimationManager.INSTANCE.getAmplitudeSliderValue(initialRadius);
            if (initialRadiusValue > 200.0) {
                initialRadiusValue = 200.0;
            }
        } else {
            initialRadiusValue = initialRadius.getValue();
        }
        //see if size ratio animation is active, if so calculate possible size ratio - base value + amplitude
        double sizeRatioValue;
        if (sizeRatio.getActiveStatus()) {
            sizeRatioValue = sizeRatio.getAnimationBaseValue().doubleValue()
                    + AnimationManager.INSTANCE.getAmplitudeSliderValue(sizeRatio);
            if (sizeRatioValue > 70.0) {
                sizeRatioValue = 70.0;
            }
        } else {
            sizeRatioValue = sizeRatio.getValue();
        }
        //calculate the number of circles based on maximum values
        int maxPossibleRecursions = calculationManager.calculateExpectedRecursionDepth(initialRadiusValue, recursionDepth.get(),
                sizeRatioValue);
        return (int) calculationManager.calculateExpectedCirclesCount(maxPossibleRecursions, childCount.getValue());
    }

    /**
     * All animatable properties
     *
     * @return the list observable list of all animatable properties
     */
    public ObservableList<Animatable> getAnimatableList() {
       return FXCollections.observableArrayList(animatableList);
    }

    /**
     * Activates the final line width by activating its checkbox
     */
    public void activateFinalLineWidth() {
        lineWidthFinalCheckBox.setSelected(true);
    }

    /**
     * Activates the final opacity by activating its checkbox
     */
    public void activateFinalOpacity() {
        opacityFinalCheckBox.setSelected(true);
    }

    /**
     * This method returns the limit for the number of circles to be drawn
     *
     * @return maximum circles limit
     */
    public long getMaximumCirclesLimit() {
        return maximumCirclesLimit.get();
    }

    /**
     * This method returns the limit for the number of circles to be drawn
     *
     * @return maximum circles limit property
     */
    public IntegerProperty maximumCirclesLimit() {
        return maximumCirclesLimit;
    }

}
