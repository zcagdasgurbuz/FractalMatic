package circles.animation;

import circles.calculation.PropertyManager;
import circles.util.CirclesConfiguration;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;
import javafx.stage.Popup;

import java.util.ArrayList;
import java.util.List;

/**
 * Animation manager
 */
public enum AnimationManager {
    /** Singleton animation manager instance. */
    INSTANCE;

    /** The cross shape of a close button. */
    private static final String closeButtonPath = "M90.914,5.296c6.927-7.034,18.188-7.065,25.154-0.068 c6.961,6.995,6.991,18.369," +
            "0.068,25.397L85.743,61.452l30.425,30.855c6.866,6.978,6.773,18.28-0.208,25.247 c-6.983,6.964-18.21," +
            "6.946-25.074-0.031L60.669,86.881L30.395,117.58c-6.927,7.034-18.188,7.065-25.154,0.068 c-6.961-6.995-" +
            "6.992-18.369-0.068-25.397l30.393-30.827L5.142,30.568c-6.867-6.978-6.773-18.28,0.208-25.247 c6.983-6.963," +
            "18.21-6.946,25.074,0.031l30.217,30.643L90.914,5.296L90.914,5.296z";


    /** The shown animatable list. */
    private final ObservableList<AnimatableRangedDoubleProperty> shownAnimatableList;
    /** The animator that is going to control animatables.  */
    private final FractalAnimator animator;
    /** The halted property.*/
    private final BooleanProperty haltedProperty;
    /** The maximum number of circles that is going to be drawn the current animation configuration */
    private final IntegerProperty animationCircles;
    /** The maximum number of  circles limit for the animations. */
    private final IntegerProperty maximumCirclesLimit;
    /** Available / stand by animatable list. */
    private ObservableList<AnimatableRangedDoubleProperty> availableAnimatableList;
    /** Active/shown controls in the animation menu. */
    private final List<Control> activeControls;
    /** The animation box that contains shown animation controls. */
    private VBox animationBox;
    /** The animation menu scroll pane. */
    private ScrollPane animationScrollPane;

    /**
     * Menu item that shows available animatables.
     */
    private ComboBox<AnimatableRangedDoubleProperty> animatableComboBox;
    /**
     * The listener that is getting assigned to animator
     */
    private final ChangeListener<Object> animationListener;
    /**
     * The listener that is going to be assigned to controls when the animation is active.
     */
    private final ChangeListener<Object> animationCheckListener;

    /**
     * Constructor, initializes necessary fields
     */
    AnimationManager() {
        shownAnimatableList = FXCollections.observableArrayList();
        activeControls = new ArrayList<>();
        animator = new FractalAnimator();
        animationCircles = new SimpleIntegerProperty(0);
        haltedProperty = new SimpleBooleanProperty();
        maximumCirclesLimit = new SimpleIntegerProperty(15000);

        animationListener = (observable, oldValue, newValue) -> {
            if (checkConditions())
                PropertyManager.INSTANCE.drawRequest();
        };

        animationCheckListener = (observable, oldValue, newValue) -> {
            checkConditions();
        };

        animator.frameCountProperty().addListener(animationListener);

        animator.activeProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                PropertyManager.INSTANCE.removeListeners();
                PropertyManager.INSTANCE.addListeners(animationCheckListener);
            } else {
                PropertyManager.INSTANCE.removeListeners();
                PropertyManager.INSTANCE.addListeners();
            }
        });

    }

    //TODO - definitely get rid of contains and lower case conversation, you know the ids, you set them.
    /**
     * Sets amplitude slider limits based on the animatable.
     *
     * @param slider the slider
     */
    private static void setAmplitudeSliderLimits(Slider slider) {
        String lowerCaseId = slider.getId().toLowerCase();
        slider.setMin(1);
        slider.setValue(10);
        slider.setBlockIncrement(1);
        if (lowerCaseId.contains("angle")) {
            slider.setMax(999);
            slider.setValue(90);
        } else if (lowerCaseId.contains("radius")) {
            slider.setMax(100);
        } else if (lowerCaseId.contains("ratio")) {
            slider.setMax(30);
        } else if (lowerCaseId.contains("line")) {
            slider.setMax(5);
            slider.setMin(0.1);
            slider.setValue(1);
            slider.setBlockIncrement(0.1);
        } else if (lowerCaseId.contains("opacity")) {
            slider.setMax(0.5);
            slider.setMin(0.01);
            slider.setValue(0.2);
            slider.setBlockIncrement(0.05);
        }
    }

    /**
     * Retrieves available animatable list.
     *
     * @return the available animatable list
     */
    public ObservableList<AnimatableRangedDoubleProperty> getAvailableAnimatableList() {
        return availableAnimatableList;
    }

    /**
     * Sets available animatable list .
     *
     * @param animatableList the animatable list
     */
    public void setAvailableAnimatableList(ObservableList<AnimatableRangedDoubleProperty> animatableList) {
        this.availableAnimatableList = animatableList;
        FXCollections.sort(availableAnimatableList);
    }

    /**
     * Sets animation box, to be used in the manager class.
     *
     * @param animationBox the animation box
     */
    public void setAnimationBox(VBox animationBox) {
        this.animationBox = animationBox;
    }

    /**
     * Sets animation combo box, to be used in the manager class.
     *
     * @param animatableComboBox the animatable combo box
     */
    public void setAnimationComboBox(ComboBox<AnimatableRangedDoubleProperty> animatableComboBox) {
        this.animatableComboBox = animatableComboBox;
    }

    /**
     * Gets the maximum number of circles that is going to be generated by current configuration
     *
     * @return the animation circles property
     */
    public IntegerProperty getAnimationCirclesProperty() {
        return animationCircles;
    }

    /**
     * Gets shown animatable list.
     *
     * @return the shown animatable list
     */
    public ObservableList<AnimatableRangedDoubleProperty> getShownAnimatableList() {
        return shownAnimatableList;
    }

    /**
     * Request start of an animation of animatable.
     *
     * @param animatable the animatable that is going to be started
     * @return true if the animation can be started otherwise false
     */
    public boolean requestStart(AnimatableRangedDoubleProperty animatable) {
        String id = animatable.toString().toLowerCase();
        if (id.contains("final")) {
            if (id.contains("line")) {
                PropertyManager.INSTANCE.activateFinalLineWidth();
            } else {
                PropertyManager.INSTANCE.activateFinalOpacity();
            }
        }
        int circles = PropertyManager.INSTANCE.getAnimationMaxCircleCount();
        animationCircles.set(circles);
        if (circles > maximumCirclesLimit.get()) {
            return false;
        } else {
            animatable.setAnimator(animator);
            animatable.start();
            return true;
        }
    }

    /**
     * Request stop an animation of animatable
     *
     * @param animatable the animatable
     */
    public void requestStop(AnimatableRangedDoubleProperty animatable) {
        animatable.stop();
        checkConditions();
    }

    /**
     * Request starts of all shown animatables.
     */
    public void startShownAnimatables() {
        for (AnimatableRangedDoubleProperty animatable : shownAnimatableList) {
            if (!animatable.getActiveStatus()) {
                for (Node singleBox : animationBox.getChildren()) {
                    for (Node child : ((VBox) singleBox).getChildren()) {
                        if (child.getId() != null &&
                                child.getId().toLowerCase().split("_")[0].equals(animatable.getId().toLowerCase()) &&
                                child.getId().toLowerCase().contains("button")) {
                            Button target = ((Button) child);
                            target.fire();
                        }
                    }
                }
                requestStart(animatable);
            }
        }
    }

    /**
     * Request stop animation of all shown animatables.
     */
    public void stopShownAnimatables() {
        for (AnimatableRangedDoubleProperty animatable : shownAnimatableList) {
            if (animatable.getActiveStatus()) {
                for (Node singleBox : animationBox.getChildren()) {
                    for (Node child : ((VBox) singleBox).getChildren()) {
                        if (child.getId() != null &&
                                child.getId().toLowerCase().split("_")[0].equals(animatable.getId().toLowerCase()) &&
                                child.getId().toLowerCase().contains("button")) {
                            Button target = ((Button) child);
                            target.fire();
                        }
                    }
                }

            }
        }
    }

    /**
     * Requests canceling of all shown animatables - removes them from menu and puts back to animatable list
     *
     * @param animatable the animatable
     */
    public void requestCancel(AnimatableRangedDoubleProperty animatable) {
        shownAnimatableList.remove(animatable);
        availableAnimatableList.add(animatable);
        FXCollections.sort(availableAnimatableList);
        //animatable.getAnimationBehavior().resetAnimation();
        animatable.stop();
        checkConditions();
    }

    /**
     * Requests amplitude change of an animatable.
     *
     * @param animatable the animatable that its amplitude is to be changed
     * @param newValue   the new amplitude value
     */
    public void requestAmplitudeChange(Animatable animatable, Double newValue) {
        animatable.getAnimationBehavior().setRange(newValue * 2);
        checkConditions();
    }

    /**
     * Request speed change of an animatable..
     *
     * @param animatable the animatable
     * @param newValue   the new value
     * @return the boolean
     */
    public boolean requestSpeedChange(Animatable animatable, Double newValue) {
        return false;
    }

    /**
     * Check condition of the animation, halts or unhalts if necessary
     *
     * @return the boolean
     */
    public boolean checkConditions() {
        animationCircles.set(PropertyManager.INSTANCE.getAnimationMaxCircleCount());
        if (animator.activeProperty().get()) {
            if (animationCircles.get() > maximumCirclesLimit.get()) {
                haltAnimation();
                return false;
            } else {
                unhaltAnimation();
                return true;
            }
        } else {
            haltedProperty.set(false);
            return false;
        }
    }

    /**
     * Halts the animation.
     */
    private void haltAnimation() {
        haltedProperty.set(true);
        animator.stop();
    }

    /**
     * Unhalts the animation.
     */
    private void unhaltAnimation() {
        haltedProperty.set(false);
        animator.start();
    }

    /**
     * Retrieves the animator of the animation.
     *
     * @return the animator
     */
    public Animator getAnimator() {
        return animator;
    }

    //TODO - get rid of contains an lower case conversations
    /**
     * Gets amplitude slider value.
     *
     * @param id the id
     * @return the amplitude slider value
     */
    public double getAmplitudeSliderValue(String id) {
        id = id.toLowerCase();
        for (Control control : activeControls) {
            String lowerCaseId = control.getId().toLowerCase();
            if (lowerCaseId.contains(id) && lowerCaseId.contains("amplitude")) {
                return ((Slider) control).getValue();
            }
        }
        return -1;
    }

    //TODO - get rid of contains an lower case conversations
    /**
     * Gets speed slider value.
     *
     * @param id the id
     * @return the speed slider value
     */
    public double getSpeedSliderValue(String id) {
        id = id.toLowerCase();
        for (Control control : activeControls) {
            String lowerCaseId = control.getId().toLowerCase();
            if (lowerCaseId.contains(id) && lowerCaseId.contains("speed")) {
                return ((Slider) control).getValue();
            }
        }
        return -1;
    }

    /**
     * Halted property boolean property.
     *
     * @return the halted property, true when animation is halted, otherwise false
     */
    public BooleanProperty haltedProperty() {
        return haltedProperty;
    }

    /**
     * Resets the current animation, stops all animations and clears animation menu.
     */
    public void reset() {
        if (animationBox != null) {

            ObservableList<Node> contents = animationBox.getChildren();
            if (contents != null) {
                contents.clear();
            }

            activeControls.clear();
            for (AnimatableRangedDoubleProperty animatable : shownAnimatableList) {
                if (animatable.getActiveStatus()) {
                    animatable.stop();
                }
            }
            shownAnimatableList.clear();
            availableAnimatableList = PropertyManager.INSTANCE.getAnimatableList();
            FXCollections.sort(availableAnimatableList);
            animatableComboBox.setItems(availableAnimatableList);
        }
    }

    //TODO figure good id names or even better create an enum type for every type.
    /**
     * This method adds new element to animation box
     *
     * @param animatable     the animatable is to be added animation menu
     * @param amplitudeValue the amplitude value to be set
     * @param speedValue     the speed value to be set
     */
    public void addElementToAnimationBox(AnimatableRangedDoubleProperty animatable,
                                         Double amplitudeValue, Double speedValue, boolean active) {
        availableAnimatableList.remove(animatable);
        shownAnimatableList.add(animatable);
        //create controls and add it to animation menu
        VBox newBox = new VBox();
        String id = animatable.toString();
        newBox.setId(id + "_animationBox");
        Label name = new Label(id);
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS); // spacer between title and close button
        SVGPath closeButtonSVG = new SVGPath(); //close button svg
        closeButtonSVG.setContent(closeButtonPath); // path is getting added
        Region closeButton = new Region(); // this is going to be actual close button
        closeButton.setShape(closeButtonSVG); // region is converted to the svg shape
        closeButton.getStyleClass().add("animationCloseButton");
        closeButton.setId(id + "_animationCloseButton");
        //closeButton.setOnMouseClicked();  // close function
        HBox nameAndCloseButtonBox = new HBox(name, spacer, closeButton);
        Label amplitudeLabel = new Label("Amplitude");
        Slider amplitudeSlider = new Slider();
        amplitudeSlider.setId(id + "_amplitudeSlider");
        setAmplitudeSliderLimits(amplitudeSlider);
        if (amplitudeValue != null) {
            amplitudeSlider.setValue(amplitudeValue);
        }
        //amplitudeSlider.setSnapToTicks(true);
        amplitudeSlider.prefWidthProperty().bind(newBox.widthProperty().subtract(57.0));
        Text amplitudeText = new Text();
        amplitudeText.getStyleClass().add("sliderValueText");
        amplitudeText.setWrappingWidth(60);
        amplitudeText.textProperty().bind(Bindings.format("%4.2f", amplitudeSlider.valueProperty()));
        HBox animationAmplitudeBox = new HBox(amplitudeSlider, amplitudeText);
        animationAmplitudeBox.setSpacing(10);
        Label speedLabel = new Label("Speed");
        Slider speedSlider = new Slider(0.01, 10.0, 1.0);
        animatable.getAnimationBehavior().setExponentialSpeed(true);
        animatable.getAnimationBehavior().setExponentialRange(speedSlider.getMin(), speedSlider.getMax());
        animatable.getAnimationBehavior().setExponentialConstant(2.0);
        if (animatable.getId().toLowerCase().contains("start"))
            animatable.getAnimationBehavior().setExponentialConstant(7.0);
        //
        speedSlider.setId(id + "_speedSlider");
        speedSlider.setBlockIncrement(0.05);
        speedSlider.prefWidthProperty().bind(newBox.widthProperty().subtract(57.0));
        if (speedValue != null) {
            speedSlider.setValue(speedValue);
        }
        Text speedText = new Text();
        speedText.getStyleClass().add("sliderValueText");
        speedText.setWrappingWidth(60);
        speedText.textProperty().bind(Bindings.format("%3.2f", speedSlider.valueProperty()));
        HBox animationSpeedBox = new HBox(speedSlider, speedText);
        animationSpeedBox.setSpacing(10);
        Button animationStartStopButton = new Button("Start");
        animationStartStopButton.setId(id + "_animationStartButton");

        newBox.setSpacing(10);
        //newBox.setPadding(new Insets(5,5,5,5));
        newBox.getChildren().addAll(nameAndCloseButtonBox, amplitudeLabel, animationAmplitudeBox, speedLabel,
                animationSpeedBox, animationStartStopButton);

        newBox.setBackground(new Background(
                new BackgroundFill(new Color(0, 0, 0, 0.2), new CornerRadii(10),
                        new Insets(-5, -5, -5, -5))));

        amplitudeSlider.valueProperty().addListener((observable, oldValue, newValue) ->
                requestAmplitudeChange(animatable, newValue.doubleValue()));

        speedSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            //there is no need to control value since speed does not affects the number of circles
            animatable.getAnimationBehavior().setSpeed(newValue.doubleValue());
        });

        animationStartStopButton.setOnAction(event -> {
            animatable.getAnimationBehavior().setRange(amplitudeSlider.getValue() * 2);
            animatable.getAnimationBehavior().setSpeed(speedSlider.getValue());
            if (animationStartStopButton.getText().equals("Start")) {
                if (requestStart(animatable)) {
                    animationStartStopButton.setText("Stop");
                } else {
                    showQuickPopupMessageIfVisible(animationStartStopButton, "Cannot start animation with this configuration.\n" +
                            "Try to reduce # of circles to be drawn.", true);
                }
            } else {
                animationStartStopButton.setText("Start");
                requestStop(animatable);
            }
        });

        closeButton.setOnMouseClicked(event -> {
            animationBox.getChildren().remove(newBox);
            activeControls.remove(amplitudeSlider);
            activeControls.remove(speedSlider);
            activeControls.remove(animationStartStopButton);
            requestCancel(animatable);
        });

        //finalize
        animationBox.getChildren().add(newBox);
        activeControls.add(amplitudeSlider);
        activeControls.add(speedSlider);
        activeControls.add(animationStartStopButton);

        if (active) {
            animationStartStopButton.fire();
            //requestStart(animatable);
        }
    }


    /**
     * Retrieves the current animation config.
     *
     * @param configuration the configuration is to be written on
     */
    public void getCurrentConfig(CirclesConfiguration configuration) {
        configuration.setAnimationMaximumCircles(maximumCirclesLimit.get());
        if (animator.activeProperty().get()) {
            configuration.setAnimationActive(true);
            for (AnimatableRangedDoubleProperty animatable : shownAnimatableList) {
                if (animatable.getActiveStatus()) {
                    String id = animatable.getId().toLowerCase();
                    if (id.contains("start")) {
                        configuration.setStartAngleAnimationActive(true);
                        configuration.setStartAngle(animatable.getAnimationBaseValue().doubleValue());
                        configuration.setStartAngleAnimationAmplitude(getAmplitudeSliderValue(id));
                        configuration.setStartAngleAnimationSpeed(getSpeedSliderValue(id));
                    } else if (id.contains("initial")) {
                        configuration.setInitialRadiusAnimationActive(true);
                        configuration.setInitialRadius(animatable.getAnimationBaseValue().doubleValue());
                        configuration.setInitialRadiusAnimationAmplitude(getAmplitudeSliderValue(id));
                        configuration.setInitialRadiusAnimationSpeed(getSpeedSliderValue(id));
                    } else if (id.contains("ratio")) {
                        configuration.setSizeRatioAnimationActive(true);
                        configuration.setSizeRatio(animatable.getAnimationBaseValue().doubleValue());
                        configuration.setSizeRatioAnimationAmplitude(getAmplitudeSliderValue(id));
                        configuration.setSizeRatioAnimationSpeed(getSpeedSliderValue(id));
                    } else if (id.contains("line") && !id.contains("final")) {
                        configuration.setLineWidthAnimationActive(true);
                        configuration.setLineWidth(animatable.getAnimationBaseValue().doubleValue());
                        configuration.setLineWidthAnimationAmplitude(getAmplitudeSliderValue(id));
                        configuration.setLineWidthAnimationSpeed(getSpeedSliderValue(id));
                    } else if (id.contains("line") && id.contains("final")) {
                        configuration.setLineWidthFinalAnimationActive(true);
                        configuration.setLineWidthFinal(animatable.getAnimationBaseValue().doubleValue());
                        configuration.setLineWidthFinalAnimationAmplitude(getAmplitudeSliderValue(id));
                        configuration.setLineWidthFinalAnimationSpeed(getSpeedSliderValue(id));
                    } else if (id.contains("opacity") && !id.contains("final")) {
                        configuration.setOpacityAnimationActive(true);
                        configuration.setOpacity(animatable.getAnimationBaseValue().doubleValue());
                        configuration.setOpacityAnimationAmplitude(getAmplitudeSliderValue(id));
                        configuration.setOpacityAnimationSpeed(getSpeedSliderValue(id));
                    } else if (id.contains("opacity") && id.contains("final")) {
                        configuration.setOpacityFinalAnimationActive(true);
                        configuration.setOpacityFinal(animatable.getAnimationBaseValue().doubleValue());
                        configuration.setOpacityFinalAnimationAmplitude(getAmplitudeSliderValue(id));
                        configuration.setOpacityFinalAnimationSpeed(getSpeedSliderValue(id));
                    }
                }
            }
        }
    }


    /**
     * Sets the current animation config.
     *
     * @param configuration the configuration is to be applied
     */
    public void setCurrentConfig(CirclesConfiguration configuration) {

        reset();
        maximumCirclesLimit.set(configuration.getAnimationMaximumCircles());

        if (configuration.isAnimationActive()) {
            if (configuration.isStartAngleAnimationActive()) {
                addElementToAnimationBox(
                        getAnimatableFromAvailableList("start angle"),
                        configuration.getStartAngleAnimationAmplitude(),
                        configuration.getStartAngleAnimationSpeed(), true);
            }
            if (configuration.isInitialRadiusAnimationActive()) {
                addElementToAnimationBox(
                        getAnimatableFromAvailableList("initial radius"),
                        configuration.getInitialRadiusAnimationAmplitude(),
                        configuration.getInitialRadiusAnimationSpeed(), true);
            }
            if (configuration.isSizeRatioAnimationActive()) {
                addElementToAnimationBox(
                        getAnimatableFromAvailableList("size ratio"),
                        configuration.getSizeRatioAnimationAmplitude(),
                        configuration.getSizeRatioAnimationSpeed(), true);
            }
            if (configuration.isLineWidthFinalAnimationActive()) {
                addElementToAnimationBox(
                        getAnimatableFromAvailableList("final line width"),
                        configuration.getLineWidthFinalAnimationAmplitude(),
                        configuration.getLineWidthFinalAnimationSpeed(), true);
            }
            if (configuration.isLineWidthAnimationActive()) {
                addElementToAnimationBox(
                        getAnimatableFromAvailableList("line width"),
                        configuration.getLineWidthAnimationAmplitude(),
                        configuration.getLineWidthAnimationSpeed(), true);
            }
            if (configuration.isOpacityFinalAnimationActive()) {
                addElementToAnimationBox(
                        getAnimatableFromAvailableList("final opacity"),
                        configuration.getOpacityFinalAnimationAmplitude(),
                        configuration.getOpacityFinalAnimationSpeed(), true);
            }
            if (configuration.isOpacityAnimationActive()) {
                addElementToAnimationBox(
                        getAnimatableFromAvailableList("opacity"),
                        configuration.getOpacityAnimationAmplitude(),
                        configuration.getOpacityAnimationSpeed(), true);
            }
        }
    }

    //TODO - Get rid of contains and lowercase conversations
    /**
     * Gets animatable from available list.
     *
     * @param id the id of the animatable
     * @return the animatable from available list
     */
    public AnimatableRangedDoubleProperty getAnimatableFromAvailableList(String id) {
        id = id.toLowerCase();
        for (AnimatableRangedDoubleProperty animatable : availableAnimatableList) {
            if (animatable.getId().toLowerCase().equals(id)) {
                return animatable;
            }
        }
        return null;
    }


    /**
     * Shows quick popup message if the source control is visible on the animation menu. Popup will appear right side
     * of the source
     *
     * @param source  the source that sends the popup request
     * @param message the message
     * @param isError the is error
     */
    private void showQuickPopupMessageIfVisible(Control source, String message, boolean isError) {

        Label label = new Label(message);
        Rectangle container = new Rectangle();
        container.getStyleClass().add("popup-container");
        container.widthProperty().bind(label.widthProperty());
        container.heightProperty().bind(label.heightProperty());
        //container.widthProperty().bind();

        Popup popup = new Popup();
        popup.getContent().add(container);
        popup.getContent().add(label);

        Point2D anchorPoint = source.localToScreen(
                source.getWidth() + 2,
                2
        );
        Point2D upperLeft = animationScrollPane.localToScreen(0, 0);
        Point2D lowerRight = new Point2D(upperLeft.getX() + animationScrollPane.getWidth(),
                upperLeft.getY() + animationScrollPane.getHeight());

        if (anchorPoint.getX() >= upperLeft.getX() && anchorPoint.getY() >= upperLeft.getY() &&
                anchorPoint.getX() <= lowerRight.getX() && anchorPoint.getY() <= lowerRight.getY()) {
            popup.show(source.getScene().getWindow(),
                    anchorPoint.getX(),
                    anchorPoint.getY()
            );
        }

        popup.setAutoHide(true);
        if (isError) {
            label.setTextFill(Color.RED);
        }
    }


    /**
     * Gets animation maximum circles limit.
     *
     * @return the maximum circles limit
     */
    public int getMaximumCirclesLimit() {
        return maximumCirclesLimit.get();
    }


    /**
     * Sets animation menu scroll pane. to be used in the manager
     *
     * @param animationScrollPane the animation scroll pane
     */
    public void setAnimationScrollPane(ScrollPane animationScrollPane) {
        this.animationScrollPane = animationScrollPane;
    }


    /**
     * Animation maximum circles limit integer property.
     *
     * @return the animation maximum circles property
     */
    public IntegerProperty maximumCirclesLimit() {
        return maximumCirclesLimit;
    }

}
