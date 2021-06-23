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
