package circles.calculation;

import circles.animation.AnimatableRangedDoubleProperty;
import circles.animation.AnimationManager;
import circles.animation.OscillationBehavior;
import circles.util.CirclesConfiguration;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.binding.*;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This singleton class handles the bindings of controls and some various calculations
 */
public enum PropertyManager {
    INSTANCE; //singleton manager

    private final boolean VERBOSE = false;

    private final ObjectProperty<Color> backGroundColor;
    private final ObjectProperty<Color> fractalColor;
    private final ObjectProperty<Color> fractalFinalColor;
    private final ObjectProperty<Integer> childCountObjectProp;
    private final IntegerProperty childCount;
    private final ObjectProperty<Integer> recursionDepthObjectProp;
    private final IntegerProperty recursionDepth;
    private final AnimatableRangedDoubleProperty startAngle;
    private final AnimatableRangedDoubleProperty initialRadius;
    private final AnimatableRangedDoubleProperty sizeRatio;
    private final AnimatableRangedDoubleProperty lineWidth;
    private final AnimatableRangedDoubleProperty lineWidthFinal;
    private final AnimatableRangedDoubleProperty opacity;
    private final AnimatableRangedDoubleProperty opacityFinal;
    private final IntegerBinding maxAllowedRecursions;
    private final IntegerProperty maximumCirclesLimit;
    private final DoubleBinding numberOfCirclesToBeDrawn;
    private final BooleanBinding okToDraw;
    private final ChangeListener<Object> changeListener;
    private final BooleanProperty drawingEnabledProperty;
    CalculationManager calculationManager = CalculationManager.INSTANCE;
    AnimationManager animationManager = AnimationManager.INSTANCE;
    private CheckBox fractalColorFinalCheckBox;
    private CheckBox lineWidthFinalCheckBox;
    private CheckBox opacityFinalCheckBox;
    private Map<String, Control> configControls;
    private List<Observable> propertyList;
    private ObservableList<AnimatableRangedDoubleProperty> animatableList;

    /**
     * Constructor, initializes the fields
     */
    PropertyManager() {
        changeListener = new ChangeListener<Object>() {
            @Override
            public void changed(ObservableValue<?> observableValue, Object o, Object t1) {
                drawRequest();
                if(VERBOSE) System.out.println("draw requested in property manager - change listener");
            }
        };
        animatableList = FXCollections.observableArrayList();
        propertyList = new ArrayList<>();
        drawingEnabledProperty = new SimpleBooleanProperty(true);

        //initialize the fields
        backGroundColor = new SimpleObjectProperty<>();
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

        ObservableList<AnimatableRangedDoubleProperty> copyList = FXCollections.observableArrayList(animatableList);
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
        ((ColorPicker) configControls.get("background_color_picker")).valueProperty().bindBidirectional(backGroundColor);
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
        //removeListeners();
        maximumCirclesLimit.set(configuration.getConfigurationMaximumCircles());
        animationManager.reset();
        backGroundColor.set(configuration.getBackgroundColor());
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
        //addListeners();
        //drawRequest();
    }

    /**
     * This method retrieves the current configuration
     *
     * @return the current configuration
     */
    public CirclesConfiguration getCurrentConfiguration(){
        CirclesConfiguration currentConfig = new CirclesConfiguration();
        getCurrentConfiguration(currentConfig);
        return currentConfig;
    }

    /**
     * This method retrieves the current configuration, writes the current config on passed configuration
     *
     * @param config the config to be written on
     * @return  the current configuration
     */
    public void getCurrentConfiguration(CirclesConfiguration config){

        config.setConfigurationMaximumCircles(maximumCirclesLimit.get());
        config.setBackgroundColor(backGroundColor.get());
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
        if(drawingEnabledProperty.get()) {
            Platform.runLater(() -> {
                calculationManager.drawRequest(backGroundColor.get(),
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
                        opacityFinal.get());
            });
        }
    }

    /**
     * Adds listeners to all the config controls
     */
    public void addListeners() {
        backGroundColor.addListener(changeListener);
        fractalColor.addListener(changeListener);
        fractalFinalColor.addListener(changeListener);
        fractalColorFinalCheckBox.selectedProperty().addListener(changeListener);
        childCount.addListener(changeListener);
        recursionDepth.addListener(changeListener);
        startAngle.addListener(changeListener);
        initialRadius.addListener(changeListener);
        sizeRatio.addListener(changeListener);
        lineWidth.addListener(changeListener);
        lineWidthFinal.addListener(changeListener);
        lineWidthFinalCheckBox.selectedProperty().addListener(changeListener);
        opacity.addListener(changeListener);
        opacityFinal.addListener(changeListener);
        opacityFinalCheckBox.selectedProperty().addListener(changeListener);
        //numberOfCirclesToBeDrawn.addListener(changeListener);
    }

    /**
     * Adds custom listeners to all the config controls
     */
    public void addListeners(ChangeListener<Object> customChangeListener) {
        backGroundColor.addListener(customChangeListener);
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
        //numberOfCirclesToBeDrawn.addListener(customChangeListener);
    }

    /**
     * Removes listeners from all the config controls
     */
    public void removeListeners() {
        backGroundColor.removeListener(changeListener);
        fractalColor.removeListener(changeListener);
        fractalFinalColor.removeListener(changeListener);
        fractalColorFinalCheckBox.selectedProperty().removeListener(changeListener);
        childCount.removeListener(changeListener);
        recursionDepth.removeListener(changeListener);
        startAngle.removeListener(changeListener);
        initialRadius.removeListener(changeListener);
        sizeRatio.removeListener(changeListener);
        lineWidth.removeListener(changeListener);
        lineWidthFinal.removeListener(changeListener);
        lineWidthFinalCheckBox.selectedProperty().removeListener(changeListener);
        opacity.removeListener(changeListener);
        opacityFinal.removeListener(changeListener);
        opacityFinalCheckBox.selectedProperty().removeListener(changeListener);
        //numberOfCirclesToBeDrawn.removeListener(changeListener);
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
        ObservableList<AnimatableRangedDoubleProperty> list = AnimationManager.INSTANCE.getShownAnimatableList();
        double sliderValue = 0;
        if (initialRadius.getActiveStatus()) {
            initialRadiusValue = initialRadius.getAnimationBaseValue().doubleValue()
                    + AnimationManager.INSTANCE.getAmplitudeSliderValue("radius");
            if (initialRadiusValue > 200.0) {
                initialRadiusValue = 200.0;
            }
            //+ initialRadius.getAnimationBehavior().getRange() / 2.0;
        } /*else if ((sliderValue=AnimationManager.INSTANCE.getAmplitudeSliderValue("radius")) >= 0 ) {

            initialRadiusValue = initialRadius.getValue() + sliderValue;
            if (initialRadiusValue > 200.0) {
                initialRadiusValue = 200.0;
            }
        }*/ else {
            initialRadiusValue = initialRadius.getValue();
        }

        double sizeRatioValue;
        if (sizeRatio.getActiveStatus()) {
            sizeRatioValue = sizeRatio.getAnimationBaseValue().doubleValue()
                    + AnimationManager.INSTANCE.getAmplitudeSliderValue("ratio");
            if (sizeRatioValue > 70.0) {
                sizeRatioValue = 70.0;
            }
            //+ sizeRatio.getAnimationBehavior().getRange() / 2.0;
        } /*else if ((sliderValue=AnimationManager.INSTANCE.getAmplitudeSliderValue("ratio")) >= 0 ){
            sizeRatioValue = sizeRatio.getValue() + sliderValue;
            if (sizeRatioValue > 70.0) {
                sizeRatioValue = 70.0;
            }
        }*/ else {
            sizeRatioValue = sizeRatio.getValue();
        }

        int maxPossibleRecursions = calculationManager.calculateExpectedRecursionDepth(initialRadiusValue, recursionDepth.get(),
                sizeRatioValue);
        return (int) calculationManager.calculateExpectedCirclesCount(maxPossibleRecursions, childCount.getValue());
    }

    /**
     *  All animatable properties
     *
     * @return the list observable list of all animatable properties
     */
    public ObservableList<AnimatableRangedDoubleProperty> getAnimatableList() {
        ObservableList<AnimatableRangedDoubleProperty> clone = FXCollections.observableArrayList();


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
    public long getMaximumCirclesLimit(){
        return maximumCirclesLimit.get();
    }

    /**
     * This method returns the limit for the number of circles to be drawn
     *
     * @return maximum circles limit property
     */
    public IntegerProperty maximumCirclesLimit(){
        return maximumCirclesLimit;
    }

}
