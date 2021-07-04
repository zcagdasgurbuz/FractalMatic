package fractalmatic.circles.ui;

import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * Sliding menu controller, handles the animations
 */
public class SlidingMenuController {

    /** The sliding menu container. */
    public StackPane slidingMenu;
    /** The hamburger menu button. */
    public JFXHamburger hamburgerMenuButton;
    /** The menu content. */
    public VBox circlesMenu;

    /** Whether the menu open/visible. */
    private boolean isOpen;
    /** The timeline of sliding animation. */
    private Timeline timeline;
    /** The hamburger menu button animation */
    private HamburgerBackArrowBasicTransition burgerTask;
    //private HamburgerSlideCloseTransition burgerTask; // different animation

    /**
     * Initialize method.
     */
    @FXML
    public void initialize() {
        //menu animation is being defined here!
        timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(slidingMenu.translateXProperty(), -305)),
                new KeyFrame(Duration.ZERO, new KeyValue(hamburgerMenuButton.translateXProperty(), 310)),
                new KeyFrame(Duration.millis(500), new KeyValue(slidingMenu.translateXProperty(), 0)),
                new KeyFrame(Duration.millis(500), new KeyValue(hamburgerMenuButton.translateXProperty(), 255))
        );
        isOpen = false;

        burgerTask = new HamburgerBackArrowBasicTransition(hamburgerMenuButton);
        //burgerTask = new HamburgerSlideCloseTransition(hamburgerMenuButton); // different animation
        burgerTask.setRate(-1);
    }

    /**
     * This method handles the menu sliding function by invoking timeline normally or backwards depends
     * on the its current position
     */
    public void menuButtonAction() {

        burgerTask.setRate(burgerTask.getRate() * -1);
        burgerTask.play();

        // adjust the direction of play and start playing, if not already done
        boolean playing = (timeline.getStatus() == Animation.Status.RUNNING);
        if (!isOpen) {
            timeline.setRate(1);
            if (!playing) {
                timeline.playFromStart();
            }
            isOpen = true;
        } else {
            timeline.setRate(-1);
            if (!playing) {
                timeline.playFrom("end");
            }
            isOpen = false;
        }
    }
}
