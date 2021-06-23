package circles.ui;

import circles.animation.AnimationManager;
import circles.calculation.PropertyManager;
import circles.util.CirclesConfiguration;
import circles.util.ConfigurationManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Popup;
import javafx.util.converter.NumberStringConverter;

import java.io.IOException;

/**
 * Save/Load/Randomizer menu controller
 */
public class SaveLoadMenuController {

    /** The config name field -for saving. */
    public TextField configNameField;
    /** The config names list view -saved configurations list */
    public ListView<CirclesConfiguration> configNamesListView;
    /** The save button. */
    public Button saveButton;
    /** The load button. */
    public Button loadButton;
    /** The remove button. */
    public Button removeButton;
    /** The randomize button. */
    public Button randomizeButton;
    /** The randomizer settings button. */
    public Button randomizerSettingsButton;
    /** The configuration - max number of circles limit text field. */
    public TextField configMaxCirclesLimitTextField;
    /** The animation - max number circles limit text field. */
    public TextField animationMaxCirclesLimitTextField;
    /** The start with last config check box. */
    public CheckBox startLastConfigCheckBox;

    /** The randomizer setting's container. */
    private HBox randomizer;
    /** The randomizer settings popup. */
    private Popup randomizerSettingsPopup;


    /**
     * Initializes before loading
     */
    @FXML
    public void initialize() {
        //config name text field
        configNameField.textProperty().addListener((observable, oldValue, newValue) -> saveButton.setDisable(newValue.length() <= 0));
        configNamesListView.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.intValue() >= 0) {
                loadButton.setDisable(false);
                removeButton.setDisable(false);
            } else {
                loadButton.setDisable(true);
                removeButton.setDisable(true);
            }
        });

        //initializes the randomizer settings popup
        try {
            randomizer = FXMLLoader.load(getClass().getResource("/circles/ui/Randomizer.fxml"));
        } catch (IOException e) {
            //oops
        }
        randomizer.getStyleClass().add("randomizerSettings");
        randomizerSettingsPopup = new Popup();
        randomizerSettingsPopup.getContent().add(randomizer);

        //maximum limits text fields
        configMaxCirclesLimitTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                configMaxCirclesLimitTextField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        animationMaxCirclesLimitTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                animationMaxCirclesLimitTextField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        configMaxCirclesLimitTextField.textProperty().bindBidirectional(PropertyManager.INSTANCE.maximumCirclesLimit(),
                new NumberStringConverter());
        animationMaxCirclesLimitTextField.textProperty().bindBidirectional(AnimationManager.INSTANCE.maximumCirclesLimit(),
                new NumberStringConverter());
        //start last config checkbox is handled in configuration manager
        ConfigurationManager.INSTANCE.setStartLastConfigCheckBox(startLastConfigCheckBox);
        ConfigurationManager.INSTANCE.setListView(configNamesListView); // same with list view
    }

    /**
     * Handles the save button function
     */
    @FXML
    public void saveButtonAction() {
        ConfigurationManager.INSTANCE.requestSave(saveButton, configNameField.textProperty().get());
        configNameField.textProperty().set("");
    }

    /**
     * Handles the load button function
     */
    @FXML
    public void loadButtonAction() {
        ConfigurationManager.INSTANCE.requestLoad(configNamesListView.getSelectionModel().getSelectedItem());
    }

    /**
     * Handles the remove button function
     */
    @FXML
    public void removeButtonAction() {
        ConfigurationManager.INSTANCE.requestRemove(configNamesListView.getSelectionModel().getSelectedItem());
    }

    /**
     * Handles the randomizer button function
     */
    @FXML
    public void randomizeButtonAction() {
        ConfigurationManager.INSTANCE.randomizeAndSet();
    }

    /**
     * Handles the randomizer settings button function
     */
    @FXML
    public void randomizerSettingsButtonAction() {
        showRandomizerSettings();
    }

    /**
     * Shows the randomizer settings
     */
    private void showRandomizerSettings() {
        Point2D anchorPoint = randomizerSettingsButton.localToScreen(
                randomizerSettingsButton.getWidth() + 2,
                -150
        );
        randomizerSettingsPopup.show(randomizerSettingsButton.getScene().getWindow(),
                anchorPoint.getX(),
                anchorPoint.getY()
        );
        randomizerSettingsPopup.setAutoHide(true);
    }
}
