package circles.util;

import circles.animation.AnimationManager;
import circles.calculation.CalculationManager;
import circles.calculation.PropertyManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Control;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Configuration manager, handles configuration.
 */
public enum ConfigurationManager {
    /** Instance of singleton configuration manager. */
    INSTANCE;

    /** The config file name. */
    final String CONFIG_FILE_NAME = "circlesSavedConfigs.fm";
    /** The last saved config name. */
    final String LAST_SAVED_CONFIG_NAME = "---last-saved-configuration-before-close---";

    /** The debug info. */
    final boolean VERBOSE = false;
    /** The try limit of randomizer - if randomizer fails to generate an acceptable config, tries again. */
    final int RANDOM_TRY_LIMIT = 1000;

    /** The random number generator. */
    private final Random random = new Random();
    /** The list view of saved configurations. */
    private ListView<CirclesConfiguration> listView;
    /** The saved configurations. */
    private ObservableList<CirclesConfiguration> configurations;
    /** The randomizer check boxes. -to see what's selected*/
    private HashMap<String, CheckBox> randomizerCheckBoxes;
    /** The start with last config check box. */
    private CheckBox startLastConfigCheckBox;


    /**
     * Sets list view of saved configurations
     *
     * @param listView the list view
     */
    public void setListView(ListView<CirclesConfiguration> listView) {
        this.listView = listView;
    }

    /**
     * Initializes saved configs at start.
     */
    public void initializeConfigs() {
        if (!readConfigs()) {
            configurations = FXCollections.observableArrayList();
        }
        CirclesConfiguration last = getLastSavedConfigurations();
        if (last != null) {
            PropertyManager.INSTANCE.setConfiguration(last);
            startLastConfigCheckBox.setSelected(true);
        } else {
            PropertyManager.INSTANCE.setConfiguration(getDefault());
        }
        listView.setItems(configurations);
    }


    /**
     * Sets start last config check box.
     *
     * @param startLastConfigCheckBox the start last config check box
     */
    public void setStartLastConfigCheckBox(CheckBox startLastConfigCheckBox) {
        this.startLastConfigCheckBox = startLastConfigCheckBox;
    }


    /**
     * Request save of current configuration. Does not overwrite a config before prompting user
     *
     * @param source the source control requests save
     * @param name   the name of the configuration
     */
    public void requestSave(Control source, String name) {

        CirclesConfiguration current = new CirclesConfiguration();
        current.setName(name);
        boolean okToSave;
        //prompt user if exists
        if (configurations.contains(current)) {
            okToSave = UiUtil.tools.alertYesOrNo(name + " is already exists, do you want to overwrite it?");
            if (okToSave) {
                configurations.remove(current);
            }
        } else {
            okToSave = true;
        }
        //do the thing!
        if (okToSave) {
            if (!PropertyManager.INSTANCE.getOkToDraw() || AnimationManager.INSTANCE.haltedProperty().get()) {
                UiUtil.tools.showQuickPopupMessage(source, "You cannot save an halted configuration!", true);
            } else {
                PropertyManager.INSTANCE.getCurrentConfiguration(current);
                configurations.add(current);
                writeConfigs();
            }
        }
    }

    /**
     * Request load of given configuration.
     *
     * @param configuration the configuration is to be loaded
     */
    public void requestLoad(CirclesConfiguration configuration) {
        PropertyManager.INSTANCE.setConfiguration(configuration);
    }

    /**
     * Request remove the config from the saved list. Prompts user before removing
     *
     * @param configuration the configuration is to be removed
     */
    public void requestRemove(CirclesConfiguration configuration) {
        if (UiUtil.tools.alertYesOrNo("Do you want to remove " + configuration.getName() + "?"))
            configurations.remove(configuration);
        writeConfigs();
    }


    /**
     * Reads the saved configurations from previously saved file, if finds the file assigns it to
     * current config variable.
     *
     * @return true if reading the configurations file is successful,otherwise false
     */
    private boolean readConfigs() {
        try {
            FileInputStream fileIn = new FileInputStream(CONFIG_FILE_NAME);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            @SuppressWarnings("unchecked")
            ArrayList<CirclesConfiguration> input = (ArrayList<CirclesConfiguration>) objectIn.readObject();
            configurations = FXCollections.observableArrayList(input);
            objectIn.close();
            fileIn.close();
            if (VERBOSE) System.out.println("config read in run");
            return true;
        } catch (IOException | ClassNotFoundException e) {
            if (VERBOSE) System.out.println("config read in catch");
            return false;
        }
    }

    /**
     * Writes the current configurations to configuration file.
     *
     * @return true if writing the configurations file out is successful,otherwise false
     */
    private boolean writeConfigs() {
        try {
            FileOutputStream fileOut = new FileOutputStream(CONFIG_FILE_NAME);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            ArrayList<CirclesConfiguration> output = new ArrayList<>(configurations);
            objectOut.writeObject(output);
            objectOut.close();
            fileOut.close();
            if (VERBOSE) System.out.println("config write out run");
            return true;
        } catch (IOException ex) {
            if (VERBOSE) System.out.println("config write out catch");
            return false;
        }
    }


    /**
     * Creates the default configuration.
     *
     * @return the default configuration
     */
    public CirclesConfiguration getDefault() {
        return new CirclesConfiguration("default", Color.web("#CCE6FF"), true, Color.RED,
                Color.BLACK, 4, 7, 90, 200.0, 49.2, true,
                10.0, 0.5, false, 1, 1, false,
                false, 1, 1,
                false, 1, 1,
                false, 1, 1,
                false, 1, 1,
                false, 1, 1,
                false, 1, 1,
                false, 1, 1,
                1000000, 15000);
    }


    /**
     * Sets randomizer check boxes.
     *
     * @param randomizerCheckBoxes the randomizer check boxes
     */
    public void setRandomizerCheckBoxes(HashMap<String, CheckBox> randomizerCheckBoxes) {
        this.randomizerCheckBoxes = randomizerCheckBoxes;
    }

    /**
     * Randomizes a new configuration and sets. Gets the current configuration and randomizes requested parameters.
     * Resets the animations even though any animation configuration was not requested.
     */
    public void randomizeAndSet() {
        //get the current configuration
        CirclesConfiguration configuration = PropertyManager.INSTANCE.getCurrentConfiguration();
        //see if there is going to be animation
        boolean animationActive = isAnimationRandomizingRequested();
        // remove all animations from current config
        removeAllAnimationsFromConfiguration(configuration);
        //check limits - if animation is active then animation circles limit is current limit else configuration circles limit
        long limit = PropertyManager.INSTANCE.getMaximumCirclesLimit();
        if (animationActive) {
            configuration.setAnimationActive(true);
            limit = AnimationManager.INSTANCE.getMaximumCirclesLimit();
        }
        //current circles count is greater than limit to get in the loop
        long currentCount = limit + 1;
        // randomize elements that are not related circles quantity
        if (randomizerCheckBoxes.get("backgroundColorCheckBox").isSelected()) {
            configuration.setBackgroundColor(getRandomColor());
        }
        if (randomizerCheckBoxes.get("fractalColorCheckBox").isSelected()) {
            configuration.setFractalColor(getRandomColor());
        }
        if (randomizerCheckBoxes.get("fractalFinalColorCheckBox").isSelected()) {
            configuration.setFractalFinalColorActive(true);
            configuration.setFractalFinalColor(getRandomColor());
        }
        if (randomizerCheckBoxes.get("startAngleCheckBox").isSelected()) {
            configuration.setStartAngle(getRandomInt(0, 360));
        }
        if (randomizerCheckBoxes.get("lineWidthCheckBox").isSelected()) {
            configuration.setLineWidth(getRandomDouble(0.1, 10));
        }
        if (randomizerCheckBoxes.get("lineWidthFinalCheckBox").isSelected()) {
            configuration.setLineWidthFinalActive(true);
            configuration.setLineWidthFinal(getRandomDouble(0.1, 10));
        }
        if (randomizerCheckBoxes.get("opacityCheckBox").isSelected()) {
            configuration.setOpacity(getRandomDouble(0.01, 1.00));
        }
        if (randomizerCheckBoxes.get("opacityFinalCheckBox").isSelected()) {
            configuration.setOpacityFinalActive(true);
            configuration.setOpacityFinal(getRandomDouble(0.01, 1.00));
        }
        // randomize animation elements that are not related to circles quantity
        if (animationActive) {
            if (randomizerCheckBoxes.get("startAngleAnimationCheckBox").isSelected()) {
                configuration.setStartAngleAnimationAmplitude(getRandomInt(0, 999));
                configuration.setStartAngleAnimationSpeed(getRandomDouble(0.01, 10.00));
                configuration.setStartAngleAnimationActive(true);
            }
            if (randomizerCheckBoxes.get("lineWidthAnimationCheckBox").isSelected()) {
                configuration.setLineWidthAnimationAmplitude(getRandomDouble(0.01, 5.0));
                configuration.setLineWidthAnimationSpeed(getRandomDouble(0.01, 10.00));

                configuration.setLineWidthAnimationActive(true);
            }
            if (randomizerCheckBoxes.get("lineWidthFinalAnimationCheckBox").isSelected()) {
                configuration.setLineWidthFinalAnimationAmplitude(getRandomDouble(0.01, 5.0));
                configuration.setLineWidthFinalAnimationSpeed(getRandomDouble(0.01, 10.00));

                configuration.setLineWidthFinalAnimationActive(true);
            }
            if (randomizerCheckBoxes.get("opacityAnimationCheckBox").isSelected()) {
                configuration.setOpacityAnimationAmplitude(getRandomDouble(0.01, 0.50));
                configuration.setOpacityAnimationSpeed(getRandomDouble(0.01, 10.00));

                configuration.setOpacityAnimationActive(true);
            }
            if (randomizerCheckBoxes.get("opacityFinalAnimationCheckBox").isSelected()) {
                configuration.setOpacityFinalAnimationAmplitude(getRandomDouble(0.01, 0.50));
                configuration.setOpacityFinalAnimationSpeed(getRandomDouble(0.01, 10.00));

                configuration.setOpacityFinalAnimationActive(true);
            }
        }
        //randomize the elements that affect the number of circles, this loop will try until find acceptable number of
        //circles or until try limit is reached
        for (int tryCount = 0; tryCount < RANDOM_TRY_LIMIT && currentCount > limit; tryCount++) {
            if (randomizerCheckBoxes.get("childCountCheckBox").isSelected()) {
                configuration.setChildCount(getRandomInt(1, 13));
            }
            if (randomizerCheckBoxes.get("recursionDepthCheckBox").isSelected()) {
                configuration.setRecursions(getRandomInt(2, 10));
            }

            if (randomizerCheckBoxes.get("initialRadiusCheckBox").isSelected()) {
                configuration.setInitialRadius(getRandomDouble(5.0, 200.0));
            }
            if (randomizerCheckBoxes.get("sizeRatioCheckBox").isSelected()) {
                configuration.setSizeRatio(getRandomDouble(5.0, 70.0));
            }

            //check the number of circles is to be generated with this config, update current count
            int expectedRecursions = CalculationManager.INSTANCE.calculateExpectedRecursionDepth(
                    configuration.getInitialRadius(), configuration.getRecursions(), configuration.getSizeRatio());
            currentCount = CalculationManager.INSTANCE
                    .calculateExpectedCirclesCount(expectedRecursions, configuration.getChildCount());

            // if animation is active randomize them as well
            if (animationActive) {
                boolean initialRadius, sizeRatio;
                if (initialRadius = randomizerCheckBoxes.get("initialRadiusAnimationCheckBox").isSelected()) {
                    configuration.setInitialRadiusAnimationAmplitude(getRandomDouble(5, 100.0));
                    configuration.setInitialRadiusAnimationSpeed(getRandomDouble(0.01, 10.00));
                    configuration.setInitialRadiusAnimationActive(true);

                }
                if (sizeRatio = randomizerCheckBoxes.get("sizeRatioAnimationCheckBox").isSelected()) {
                    configuration.setSizeRatioAnimationAmplitude(getRandomDouble(1, 30));
                    configuration.setSizeRatioAnimationSpeed(getRandomDouble(0.01, 10.00));
                    configuration.setSizeRatioAnimationActive(true);
                }

                //check the quantity again for animation, update current count
                expectedRecursions = CalculationManager.INSTANCE.calculateExpectedRecursionDepth(
                        initialRadius ? configuration.getInitialRadius()
                                + configuration.getInitialRadiusAnimationAmplitude() : configuration.getInitialRadius(),
                        configuration.getRecursions(),
                        sizeRatio ? configuration.getSizeRatio()
                                + configuration.getSizeRatioAnimationAmplitude() : configuration.getSizeRatio());
                currentCount = CalculationManager.INSTANCE
                        .calculateExpectedCirclesCount(expectedRecursions, configuration.getChildCount());
            }
        }
        //do not set if an acceptable configuration couldn't be generated
        if (currentCount < limit) {
            PropertyManager.INSTANCE.setConfiguration(configuration);
        } // a popup message can be add here, need a source
    }


    /**
     * Checks whether any animation randomization is selected
     *
     * @return the boolean
     */
    private boolean isAnimationRandomizingRequested() {
        for (String id : randomizerCheckBoxes.keySet()) {
            if (id.toLowerCase().contains("animation") && randomizerCheckBoxes.get(id).isSelected()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets random double in a given range.
     *
     * @param min the min value of the range, inclusive
     * @param max the max value of the range, inclusive
     * @return the random double within the range
     */
    private double getRandomDouble(double min, double max) {
        return Math.round((min + (max - min) * random.nextDouble()) * 100) / 100.0;
    }

    /**
     * Gets random integer in a given range.
     *
     * @param min the min value of the range, inclusive
     * @param max the max value of the range, inclusive
     * @return the random int within the range
     */
    private int getRandomInt(int min, int max) {
        return min + random.nextInt(max + 1);
    }

    /**
     * Gets a random color.
     *
     * @return a random color
     */
    private Color getRandomColor() {
        return Color.rgb(getRandomInt(0, 255), getRandomInt(0, 255), getRandomInt(0, 255));
    }

    /**
     * Removes all animations from given configuration.
     *
     * @param configuration the configuration that its animations are to be removed
     */
    private void removeAllAnimationsFromConfiguration(CirclesConfiguration configuration) {
        configuration.setAnimationActive(false);
        configuration.setStartAngleAnimationActive(false);
        configuration.setInitialRadiusAnimationActive(false);
        configuration.setSizeRatioAnimationActive(false);
        configuration.setLineWidthAnimationActive(false);
        configuration.setLineWidthFinalAnimationActive(false);
        configuration.setOpacityAnimationActive(false);
        configuration.setOpacityFinalAnimationActive(false);
    }


    /**
     * Handles the saving process of the configuration if start with last config is selected
     */
    public void handleSavingBeforeClosing() {
        if (startLastConfigCheckBox.isSelected()) {
            CirclesConfiguration last = PropertyManager.INSTANCE.getCurrentConfiguration();
            last.setName(LAST_SAVED_CONFIG_NAME);
            configurations.add(last);
            writeConfigs();
        }
    }

    /**
     * Gets the last saved configurations before closing.
     *
     * @return the last saved configurations
     */
    public CirclesConfiguration getLastSavedConfigurations() {
        CirclesConfiguration last = new CirclesConfiguration(LAST_SAVED_CONFIG_NAME);
        int idx = configurations.indexOf(last);
        if( idx >= 0){
            last = configurations.get(idx);
            configurations.remove(idx);
            return last;
        }
        return null;
    }

    /**
     * Get whether the start with last configuration option is selected
     *
     * @return the boolean
     */
    public boolean shouldSaveBeforeClosing() {
        return startLastConfigCheckBox.isSelected();
    }

}
