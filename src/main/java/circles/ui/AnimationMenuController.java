package circles.ui;

import circles.animation.Animatable;
import circles.animation.AnimatableRangedDoubleProperty;
import circles.animation.AnimationManager;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

/**
 * Animation menu controller
 */
public class AnimationMenuController {

    // close button shape path
    private static final String closeButtonPath = "M90.914,5.296c6.927-7.034,18.188-7.065,25.154-0.068 c6.961,6.995,6.991,18.369," +
            "0.068,25.397L85.743,61.452l30.425,30.855c6.866,6.978,6.773,18.28-0.208,25.247 c-6.983,6.964-18.21," +
            "6.946-25.074-0.031L60.669,86.881L30.395,117.58c-6.927,7.034-18.188,7.065-25.154,0.068 c-6.961-6.995-" +
            "6.992-18.369-0.068-25.397l30.393-30.827L5.142,30.568c-6.867-6.978-6.773-18.28,0.208-25.247 c6.983-6.963," +
            "18.21-6.946,25.074,0.031l30.217,30.643L90.914,5.296L90.914,5.296z";


    public Button addComponentButton;
    public ComboBox<Animatable> animatableComboBox;
    public VBox activeAnimationVBox;
    public Button stopAllButton;
    public Button startAllButton;
    public Button removeAllButton;
    public ScrollPane animationScrollPane;

    private final AnimationManager animationManager = AnimationManager.INSTANCE;


    /**
     * Initializes before loading
     */
    @FXML
    public void initialize() {
        animationManager.setAnimationBox(activeAnimationVBox);
        animationManager.setAnimationComboBox(animatableComboBox);
        animatableComboBox.setItems(animationManager.getAvailableAnimatableList());
        animationManager.setAnimationScrollPane(animationScrollPane);
        addComponentButton.disableProperty()
                .bind(Bindings.greaterThan(0, animatableComboBox.getSelectionModel().selectedIndexProperty()));
        BooleanBinding isCancelable = new BooleanBinding() {
            {
                super.bind(activeAnimationVBox.getChildren());
            }

            @Override
            protected boolean computeValue() {
                return activeAnimationVBox.getChildren().size() < 1;
            }
        };

        removeAllButton.disableProperty().bind(isCancelable);
        startAllButton.disableProperty().bind(isCancelable);
        stopAllButton.disableProperty().bind(Bindings.not(animationManager.getAnimator().activeProperty()));
    }

    /**
     * Handles the add animation component action.
     */
    public void addButtonAction() {
        AnimatableRangedDoubleProperty selected = (AnimatableRangedDoubleProperty) animatableComboBox.getSelectionModel().getSelectedItem();
        animationManager.addElementToAnimationBox(selected, null, null, false);
    }

    /**
     * Handles the start all Button action
     */
    public void startAllButtonAction() {
        animationManager.startShownAnimatables();
    }

    /**
     * Handles the stop all Button action
     */
    public void stopAllButtonAction() {
        animationManager.stopShownAnimatables();
    }

    /**
     * Handles the remove all button action
     */
    public void removeAllButtonAction() {
        animationManager.reset();
    }
}
